package com.hanssem.remodeling.content.common.config;

import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.PageAndSortRequest;
import com.hanssem.remodeling.content.common.model.PaginationRequest;
import com.hanssem.remodeling.content.constant.ResponseCode;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public final class CustomPageableResolver implements HandlerMethodArgumentResolver {

    // 해당 리졸버에 들어올 클래스들 나열
    private static final Class[] ALLOWED_CLASSES = {
            PaginationRequest.class,
            PageAndSortRequest.class
    };

    private static final List<String> SORT_KEY_NAMES = List.of("sort", "sorts");

    private CustomPageableResolver() {
    }

    public static CustomPageableResolver newInstance() {
        return new CustomPageableResolver();
    }

    /**
     * 본 리퀘스트 타입과 상위 페이지네이션 타입을 허용합니다.
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        for (final Class classType : ALLOWED_CLASSES) {
            if (parameter.getParameterType().equals(classType)
                    || parameter.getParameterType().getSuperclass().equals(classType)
            ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final List<Sort.Order> sortList = new ArrayList<>();
        final int page = getIntValue(webRequest.getParameter("page"), 1, Integer.MAX_VALUE);
        // 안들어오면 기본값 50, 값이 들어오면 100
        final int size = getIntValue(webRequest.getParameter("size"), 50, 100);
        final String[] sorts = getSort(webRequest.getParameterMap());

        /** 웹으로 부터 정렬이 가능하거나, 정렬가능한 객체를 상속 받은 자식객체의 경우 **/
        if (parameter.getParameterType().equals(PageAndSortRequest.class)
                || parameter.getParameterType().getSuperclass().equals(PageAndSortRequest.class)) {

            if (Objects.nonNull(sorts)) {
                for (final String sort : sorts) {
                    final String[] kvPair = sort.trim().split(",");
                    sortList.add(kvPair.length == 1 ? Sort.Order.asc(kvPair[0]) :
                            kvPair[1].equalsIgnoreCase("desc") ? Sort.Order.desc(kvPair[0]) : Sort.Order.asc(kvPair[0])
                    );
                }
            }
            // 본 타입인 경우 Reflection 할꺼 없이 바로 본 타입 리턴
            if (parameter.getParameterType().equals(PageAndSortRequest.class)) {
                return PageAndSortRequest.of(page - 1, size, sortList);
            }

            /** 순수 페이지네이션만 하는 경우 **/
        } else if (parameter.getParameterType().equals(PaginationRequest.class)) {
            return PaginationRequest.of(page - 1, size, null);
        }

        /** 페이지네이션을 상속받아서 처리하는 경우**/
        // 페이지네이션 전용 생성자를 찾아 객체를 생성한다. (본 타입으로 부터 강제로 구현해야하는 Constructor은 int,int,List의 Proected 생성자)
        final Constructor<?>[] constructors = parameter.getParameterType().getDeclaredConstructors();
        for (final Constructor constructor : constructors) {
            // class는 단일 상속이기때문에 부모타입에 Pagination을 받은 객체는 무조건 인자가 3개짜리인 생성자를 강제로 오버로딩 하게되어있다.
            if (constructor.getModifiers() != Modifier.PUBLIC && constructor.getModifiers() != Modifier.PROTECTED)
                continue;
            // 인자가 3개짜리인 생성자인지 체크
            if (constructor.getParameterCount() != 3)
                continue;
            // 객체 생성을 가능하게 권한 부여
            constructor.setAccessible(true);
            // Web Param과 Object의 데이터 binding
            return setArguments(
                    // 생성자를 이용한 객체 생성
                    constructor.newInstance(page - 1, size, sortList),
                    webRequest
            );
        }
        return null;
    }


    private String[] getSort(final Map<String, String[]> parameterMap) {
        for (String sortKeyName : SORT_KEY_NAMES)
            if (parameterMap.keySet().contains(sortKeyName))
                return parameterMap.get(sortKeyName);

        return null;
    }


    /**
     * 동적 생성한 자식객체에 웹 파라미터의 키 이름으로 객체의 메서드이름을 찾아 값을 주입해주는 메서드입니다.
     *
     * @param parameterInstance Generated Dynamic Object
     * @param request           WebReqeust
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object setArguments(final Object parameterInstance, final WebRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Iterator<String> iterator = request.getParameterNames();
        String queryStringKey;
        // 파라미터 순회
        while (iterator.hasNext()) {
            // 파라미터 키네임 조회
            queryStringKey = iterator.next();
            // 매칭할 메서드의 이름 순회
            for (Field field : parameterInstance.getClass().getDeclaredFields()) {
                if (!field.getName().equals(queryStringKey)) continue;
                field.setAccessible(true);
                field.set(parameterInstance, getRealTypeParameters(field, request.getParameterMap().get(queryStringKey)));
                field.setAccessible(false);
            }
//            for (Method method : parameterInstance.getClass().getMethods()) {
//                // 해당 메서드의 이름이 파라미터 키네임과 일치한다면
//                if (method.getName().equals(String.format("%s%s", "set", capitalize(key)))) {
//                    // 해당 메서드의 매개변수 인자타입을 밝혀낸뒤 Invoke를 하여 값 주입
//                    parameterInstance.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes())
//                            .invoke(parameterInstance, getRealTypeParameters(method, request.getParameterMap().get(key)));
//                }
//            }
        }
        return parameterInstance;
    }

    /**
     * 메서드의 인자타입과 파라미터를 받아 메서드의 인자형식으로 파라미터의 값을 변환해준다.
     *
     * @param method     메서드가 원하는 타입
     * @param paramArray Web에서 들어온 파라미터이기때문에 String이다 (기본)
     * @return Converted Original Type Value
     */
    private Object getRealTypeParameters(final Field field, final String[] paramArray) {
        try {
            final Class realType = field.getType();
            if (realType.isArray()) {
                return convertArray(realType.getComponentType(), paramArray);
            } else if (realType.isEnum()) {
                return Enum.valueOf(realType, paramArray[0]);
            } else {
                return convertRealType(realType, paramArray, field);
            }
        } catch (Exception e) {
            // 각 모듈별 상황에 맞는 예외 처리를 하면됩니다.
            throw new CustomException(ResponseCode.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * 메서드의 인자타입을 이용하여 원타입으로 변환
     *
     * @param realType
     * @param paramArray
     * @return
     */
    private Object convertRealType(final Class realType, final String[] paramArray, final Field field) {
        return switch (realType.getTypeName()) {
            default -> paramArray[0];
            case "int", "java.lang.Integer" -> Integer.valueOf(paramArray[0]);
            case "long", "java.lang.Long" -> Long.valueOf(paramArray[0]);
            case "boolean", "java.lang.Boolean" -> Boolean.valueOf(paramArray[0]);
            case "float", "java.lang.Float" -> Float.valueOf(paramArray[0]);
            case "double", "java.lang.Double" -> Double.valueOf(paramArray[0]);
            case "short", "java.lang.Short" -> Short.valueOf(paramArray[0]);
            case "java.util.List" -> convertCollectionGenericType(field, paramArray);
            case "java.time.LocalDate" -> LocalDate.parse(paramArray[0]);
            case "java.time.LocalDateTime" -> LocalDateTime.parse(paramArray[0]);
        };
    }

    /**
     * QueryString 중 배열값을 객체의 Collection객체와 매핑하여 반환
     *
     * @param field
     * @param paramArray
     * @return
     */
    private Object convertCollectionGenericType(final Field field, final String[] paramArray) {
        if (Objects.isNull(paramArray)) return null;
        // 메소드인자의 컬렉션제네릭 파라미터를 조회
        final ParameterizedType genericTypeParameters = (ParameterizedType) field.getGenericType();
        // 메소드인자의 제네릭파라미터를 클래스로 변환
        final Class genericClass = ((Class) genericTypeParameters.getActualTypeArguments()[0]);

        final List<Object> resultList = new ArrayList();
        for (String s : paramArray) {
            if (genericClass.isEnum()) {
                resultList.add(Enum.valueOf(genericClass, s));
                continue;
            }
            if (paramArray[0].split(",").length > 0) {
                for (String splitedString : paramArray[0].split(",")) {
                    resultList.add(convertRealType(genericClass, new String[]{splitedString.trim()}, field));
                }
                continue;
            }
            resultList.add(convertRealType(genericClass, new String[]{s}, field));
        }
        return resultList;
    }

    private Object convertArray(final Class realType, final String[] paramArray) {
        if (String.class.getTypeName().equals(realType.getTypeName()))
            return paramArray;

        final Object resultArr = Array.newInstance(realType, paramArray.length);
        if (realType.isEnum()) {
            for (int i = 0; i < paramArray.length; i++) {
                Array.set(resultArr, i, Enum.valueOf(realType, paramArray[i]));
            }
        } else {
            if (int.class.getTypeName().equals(realType.getTypeName()) || Integer.class.getTypeName().equals(realType.getTypeName())) {
                for (int i = 0; i < paramArray.length; i++) {
                    Array.set(resultArr, i, Integer.parseInt(paramArray[i]));
                }
            }
        }
        return resultArr;
    }

    /**
     * @param param
     * @param defaultValue
     * @param maxValue
     * @return
     */
    private int getIntValue(final String param, final int defaultValue, final int maxValue) {
        if (param == null || param.isEmpty()) {
            return defaultValue;
        }
        int value = Integer.valueOf(param);
        if (value > maxValue || value < 1) {
            return defaultValue;
        }

        return value;
    }
}
