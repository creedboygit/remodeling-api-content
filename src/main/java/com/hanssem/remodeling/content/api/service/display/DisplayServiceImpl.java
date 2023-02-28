package com.hanssem.remodeling.content.api.service.display;


import static com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentWithDataResponse.DisplayAutoConfigData;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.AD_TYPE;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.AUTO_CONFIG_DATA;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.CODE;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.COMPONENT_DATA;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.COMPONENT_DATA_LIST;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.CTG_NO;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.DISP_COMPONENT_TYPE;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.IS_AUTO;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.MESSAGE;
import static com.hanssem.remodeling.content.api.service.display.util.DisplayUtil.REPLACE_CATEGORY_NO;
import static com.hanssem.remodeling.content.api.service.display.vo.HsMallComponentListVo.MallDisplayComponent;
import static com.hanssem.remodeling.content.constant.TrueFalseType.FALSE;
import static com.hanssem.remodeling.content.constant.TrueFalseType.TRUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailInfoResponse;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayComponentDataRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayComponentListRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayGnbRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayInfoRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayMenuRequest;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentListResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentWithDataResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayInfoResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayMainGnbResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayMenuResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayTemplateInfoResponse;
import com.hanssem.remodeling.content.api.repository.client.HanssemMallClient;
import com.hanssem.remodeling.content.api.service.category.mapper.CategoryMapper;
import com.hanssem.remodeling.content.api.service.category.vo.MallCategoryListVo;
import com.hanssem.remodeling.content.api.service.display.dto.DisplayGnbPageUrlDto;
import com.hanssem.remodeling.content.api.service.display.dto.DisplayGnbPageUrlDto.GnbPageUrl;
import com.hanssem.remodeling.content.api.service.display.dto.DisplayTemplateInfoDto;
import com.hanssem.remodeling.content.api.service.display.mapper.DisplayMapper;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallComponentListVo;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallGnbVo;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallGnbVo.DisplayMallGnb;
import com.hanssem.remodeling.content.api.service.display.vo.HsMallMenuVo;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.util.GlobalConstants;
import com.hanssem.remodeling.content.common.util.HsMallResUtil;
import com.hanssem.remodeling.content.constant.DisplayScreenType;
import com.hanssem.remodeling.content.constant.HsMallAllType;
import com.hanssem.remodeling.content.constant.HsMallType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisplayServiceImpl implements DisplayService {

    private final HanssemMallClient hanssemMallClient;
    private final DisplayTemplateInfoDto displayTemplateInfoDto;

    private final DisplayGnbPageUrlDto displayGnbPageUrlDto;

    private final ObjectMapper objectMapper;


    @Override
    public DisplayInfoResponse findDisplayComponentInfo(final DisplayInfoRequest displayInfoRequest, boolean is404Error) {

        log.debug("# DisplayInfoRequest: " + displayInfoRequest);
        checkPramComponentList(displayInfoRequest.getDispTemplateNo()
                , displayInfoRequest.getTemplateApiCd());

        HsMallComponentListVo hsMallComponentListVo = hanssemMallClient.findComponentList(
                displayInfoRequest.getDispTemplateNo()
                , displayInfoRequest.getCtgNo()
                , displayInfoRequest.getTemplateApiCd());

        HsMallResUtil.checkResCode(hsMallComponentListVo.getCode(), hsMallComponentListVo.getMessage(), is404Error);

        DisplayInfoResponse displayInfoResponse = DisplayInfoResponse
                .builder()
                .dispTemplateNo(hsMallComponentListVo.getDispTemplateNo())
                .templateApiCd(hsMallComponentListVo.getTemplateApiCd())
                .dispComponentList(new ArrayList<>())
                .dispComponentGdsList(DisplayMapper.INSTANCE.toDisplayComponentGdsResponseList(hsMallComponentListVo.getDispComponentListGds()))
                .build();

        List<DisplayComponentWithDataResponse> dispComponentList = displayInfoResponse.getDispComponentList();

        hsMallComponentListVo.getDispComponentList().forEach(component -> {

            DisplayComponentWithDataResponse displayComponentWithDataResponse = DisplayMapper.INSTANCE.toDisplayComponentWithDataResponse(component);

            if (FALSE.getLowerCase().equals(component.getIsAuto())) {
                displayComponentWithDataResponse.setComponentData(this.getComponentDataFromMallClient(displayInfoRequest, component));
            } else if (TRUE.getLowerCase().equals(component.getIsAuto())) {
                displayComponentWithDataResponse.setAutoConfigData(this.getSettedAutoConfigData(component));
            }

            dispComponentList.add(displayComponentWithDataResponse);
        });

        return displayInfoResponse;
    }

    private DisplayAutoConfigData getSettedAutoConfigData(final MallDisplayComponent mallDisplayComponent) {

        String url = Optional.ofNullable(mallDisplayComponent.getJspNm()).orElse("");
        String replacedUrl = url.replace(REPLACE_CATEGORY_NO, mallDisplayComponent.getCtgNo());

        if (replacedUrl.startsWith("/")) {
            String fullUrl = GlobalConstants.makeGateWayFullUrl(replacedUrl);
            return DisplayAutoConfigData.builder()
                    .url(fullUrl)
                    .build();
        }

        return DisplayAutoConfigData.builder()
                .build();
    }

    private Object getComponentDataFromMallClient(final DisplayInfoRequest displayInfoRequest,
            final MallDisplayComponent mallComponent) {

        log.debug("# mallComponent: {}", mallComponent);

        Object hsMallDisplayComponentDataObject = hanssemMallClient
                .findComponentDataList(
                        mallComponent.getDispComponentType()
                        , String.valueOf(mallComponent.getDispComponentNo())
                        , mallComponent.getComponentLayoutCd()
                        , mallComponent.getCtgNo()
                        , displayInfoRequest.getPcId()
                        , displayInfoRequest.getCustNo()
                        , displayInfoRequest.getSnbCd()
                        , displayInfoRequest.getSort()
                        , displayInfoRequest.getChannelCd().getCode());

        Map<String, Object> objectMap = objectMapper.convertValue(hsMallDisplayComponentDataObject, Map.class);
        String resCode = (String) objectMap.getOrDefault("code", "CODE");
        String resMessage = (String) objectMap.getOrDefault("message", "MESSAGE");

        if ("200".equals(resCode)) {
            return this.removeFieldCodeMessage(objectMap);
        }

        loggingComponentDataInvalidRes(resCode, resMessage, mallComponent.getIsAuto());
        return null;
    }

    private Map<String, Object> removeFieldCodeMessage(Map<String, Object> objectMap) {
        objectMap.remove(CODE);
        objectMap.remove(MESSAGE);

        return objectMap;
    }

    private void loggingComponentDataInvalidRes(String code, String message, String isAuto) {

        StringBuilder sb = new StringBuilder("# Hanssem Mall client - 컴포넌트데이터조회 응답 code: {}, message: {}");

        if ("404".equals(code)) {
            sb.append(", isAuto: {}");
            log.error(sb.toString(), code, message, isAuto);
        } else {
            log.error(sb.toString(), code, message);
        }
    }

    @Override
    public DisplayComponentListResponse findDisplayComponentList(
            final DisplayComponentListRequest displayComponentListRequest) {

        log.debug("## DisplayComponentListRequest: " + displayComponentListRequest);
        checkPramComponentList(displayComponentListRequest.getDispTemplateNo()
                , displayComponentListRequest.getTemplateApiCd());

        HsMallComponentListVo hsMallComponentListVo = hanssemMallClient.findComponentList(
                displayComponentListRequest.getDispTemplateNo()
                , displayComponentListRequest.getCtgNo()
                , displayComponentListRequest.getTemplateApiCd());

        HsMallResUtil.checkResCode(hsMallComponentListVo.getCode(), hsMallComponentListVo.getMessage(), true);

        log.debug("### hanssemMallComponentListVo: " + hsMallComponentListVo);

        DisplayComponentListResponse displayComponentListResponse = DisplayComponentListResponse
                .builder()
                .dispTemplateNo(hsMallComponentListVo.getDispTemplateNo())
                .templateApiCd(hsMallComponentListVo.getTemplateApiCd())
                .dispComponentList(new ArrayList<>())
                .build();

        List<DisplayComponentResponse> dispComponentList = displayComponentListResponse.getDispComponentList();

        hsMallComponentListVo.getDispComponentList().forEach(component -> {

            DisplayComponentResponse displayComponentResponse = DisplayMapper.INSTANCE.toDisplayComponentResponse(component);

            if (TRUE.getLowerCase().equals(component.getIsAuto())) {
                displayComponentResponse.setAutoConfigData(this.getSettedAutoConfigData(component));
            }

            dispComponentList.add(displayComponentResponse);
        });

        return displayComponentListResponse;
    }

    private void checkPramComponentList(String dispTemplateNo, String templateApiCd) {
        if (StringUtils.isBlank(dispTemplateNo)
                && StringUtils.isBlank(templateApiCd)) {
            String logMsg = "dispTemplateNo / templateApiCd 중 한가지는 필수 입니다.";
            log.error(logMsg);
            throw new CustomException(ResponseCode.BAD_REQUEST, logMsg);
        }

        if (StringUtils.isNotBlank(dispTemplateNo)
                && StringUtils.isNotBlank(templateApiCd)) {
            String logMsg = "dispTemplateNo / templateApiCd 중 한가지만 입력되어야 합니다.";
            log.error(logMsg);
            throw new CustomException(ResponseCode.BAD_REQUEST, logMsg);
        }

    }

    @Override
    public Object findDisplayComponentData(DisplayComponentDataRequest displayComponentDataRequest) {

        log.debug("## displayComponentDataRequest = " + displayComponentDataRequest);

        Object hsMallDisplayComponentDataVo = hanssemMallClient.findComponentDataList(
                displayComponentDataRequest.getDispComponentType()
                , displayComponentDataRequest.getDispComponentNo()
                , displayComponentDataRequest.getComponentLayoutCd()
                , displayComponentDataRequest.getCtgNo()
                , displayComponentDataRequest.getPcId()
                , displayComponentDataRequest.getCustNo()
                , displayComponentDataRequest.getSnbCd()
                , displayComponentDataRequest.getSort()
                , displayComponentDataRequest.getChannelCd().getCode()
                // hidden 처리된 param 제외함.
        );

        log.debug("## displayComponentVo = " + hsMallDisplayComponentDataVo);

        Map<String, Object> objectMap = objectMapper.convertValue(hsMallDisplayComponentDataVo, Map.class);

        String resCode = (String) objectMap.getOrDefault("code", "CODE");
        String resMessage = (String) objectMap.getOrDefault("message", "MESSAGE");

        HsMallResUtil.checkResCode(resCode, resMessage, true);

        return this.removeFieldCodeMessage(objectMap);
    }

    @Override
    public List<DisplayMainGnbResponse> findDisplayGnb(DisplayGnbRequest displayGnbRequest) {

        log.debug("displayGnbRequest: {}", displayGnbRequest);

        String viewType = displayGnbRequest.getViewType().name();
        String typeValue = displayGnbRequest.getTypeValue().getCode();

        HsMallGnbVo hsMallGnbVo = hanssemMallClient.findTemplateGnbV5List(viewType, typeValue);

        log.debug("hsMallGnbVo: {}", hsMallGnbVo);
        HsMallResUtil.checkResCode(hsMallGnbVo.getCode(), hsMallGnbVo.getMessage(), true);

        List<DisplayMainGnbResponse> displayMainGnbResponseList = DisplayMapper.INSTANCE.toDisplayMainGnbResponseList(hsMallGnbVo.getGnbList());
        setGnbPageUrl(displayMainGnbResponseList, displayGnbRequest.getTypeValue());

        return displayMainGnbResponseList;
    }

    private void setGnbPageUrl(List<DisplayMainGnbResponse> displayMainGnbResponseList, DisplayScreenType displayScreenType) {

        Map<String, String> pageUrlMap = displayGnbPageUrlDto.getPageUrlList()
                .stream()
                .collect(Collectors.toMap(
                        GnbPageUrl::getTemplateNo,
                        GnbPageUrl::getPageUrl
                ));

        for (DisplayMainGnbResponse displayMainGnbResponse : displayMainGnbResponseList) {
            String pageUrl = pageUrlMap.getOrDefault(displayMainGnbResponse.getDispTemplateNo(), "");
            String pageFullUrl = GlobalConstants.makePageFullUrl(pageUrl, displayScreenType);
            displayMainGnbResponse.setPageUrl(pageFullUrl);
        }
    }

    @Override
    public List<DisplayTemplateInfoResponse> findDisplayTemplateInfo() {

        return getDisplayTemplateInfoFromReflection();
    }

    private List<DisplayTemplateInfoResponse> getDisplayTemplateInfoFromReflection() {

        return Arrays.stream(displayTemplateInfoDto.getClass().getDeclaredFields())
                .map(f -> {
                    f.setAccessible(true);

                    String value = null;
                    try {
                        value = String.valueOf(f.get(displayTemplateInfoDto));
                    } catch (IllegalAccessException e) {
                        log.error("Reflection Error: getDisplayTemplateInfoFromReflection");
                    }

                    return DisplayTemplateInfoResponse.builder()
                            .key(f.getName())
                            .value(value)
                            .build();

                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DisplayComponentWithDataResponse> getDisplayDataSamples(Boolean isRefresh) {

        List<DisplayComponentWithDataResponse> allDataList = new ArrayList<>();

        if (allDataList.isEmpty() || isRefresh) {
            setTemplateNo(allDataList, List.of("435", "452"));
            setResultMapFromGnb(allDataList);
            setResultMapFromCategory(allDataList);
        }

        return allDataList;
    }

    public Map<String, Object> processLayoutCd(List<DisplayComponentWithDataResponse> allDataList) {

        Map<String, Object> resultMap = new HashMap<>();

        for (DisplayComponentWithDataResponse displayComponentWithData : allDataList) {

            String componentLayoutCd = displayComponentWithData.getComponentLayoutCd();
            Object componentData = displayComponentWithData.getComponentData();
            DisplayAutoConfigData autoConfigData = displayComponentWithData.getAutoConfigData();

            if (!resultMap.containsKey(componentLayoutCd)) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put(AD_TYPE, displayComponentWithData.getAdType());
                dataMap.put(IS_AUTO, displayComponentWithData.getIsAuto());
                dataMap.put(CTG_NO, displayComponentWithData.getCtgNo());
                dataMap.put(COMPONENT_DATA, componentData);
                dataMap.put(AUTO_CONFIG_DATA, autoConfigData);

                resultMap.put(componentLayoutCd, dataMap);

            } else {
//                if (TrueFalseType.TRUE.getLowerCase().equals(displayComponentWithData.getIsAuto())) {
//                    continue;
//                }

                if (componentData == null) {
                    if (FALSE.getLowerCase().equals(displayComponentWithData.getIsAuto())) {
                        log.error("#### check - isAuto:false, componentData 없음. dispComponentType: {}, componentLayoutCd: {}"
                                , displayComponentWithData.getDispComponentType()
                                , displayComponentWithData.getComponentLayoutCd());
                    }
                    continue;
                }

                Map<String, Object> dataMap = objectMapper.convertValue(componentData, Map.class);
                List<Object> dataList = (List<Object>) dataMap.get(COMPONENT_DATA_LIST);
                if (dataList.size() < 1) {
                    dataMap.put(AD_TYPE, displayComponentWithData.getAdType());
                    dataMap.put(IS_AUTO, displayComponentWithData.getIsAuto());
                    dataMap.put(CTG_NO, displayComponentWithData.getCtgNo());
                    dataMap.put(COMPONENT_DATA, componentData);
                    dataMap.put(AUTO_CONFIG_DATA, autoConfigData);

                    resultMap.put(componentLayoutCd, dataMap);
                }
            }
        }

        return resultMap;
    }

    public Map<String, Object> processAdType(List<DisplayComponentWithDataResponse> dispComponentList) {

        Map<String, Object> resultMap = new HashMap<>();

        for (DisplayComponentWithDataResponse displayComponentWithData : dispComponentList) {

            String adType = displayComponentWithData.getAdType();
            Map<String, Object> map = new HashMap<>();

            map.put(AD_TYPE, adType);
            map.put(DISP_COMPONENT_TYPE, displayComponentWithData.getDispComponentType());
            map.put(IS_AUTO, displayComponentWithData.getIsAuto());
            map.put(CTG_NO, displayComponentWithData.getCtgNo());
            map.put(COMPONENT_DATA, displayComponentWithData.getComponentData());
            map.put(AUTO_CONFIG_DATA, displayComponentWithData.getAutoConfigData());

            if (!resultMap.containsKey(adType)) {
                List<Map<String, Object>> dataList = new ArrayList<>();
                dataList.add(map);
                resultMap.put(adType, dataList);

            } else {
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get(adType);
                dataList.add(map);
            }
        }

        return resultMap;
    }

    private void setTemplateNo(List<DisplayComponentWithDataResponse> allDataList, List<String> templateNoList) {

        templateNoList.forEach(templateNo -> {
            callDisplayApiAndGetDisplayData(allDataList, templateNo, "");
        });
    }

    private void setResultMapFromGnb(List<DisplayComponentWithDataResponse> allDataList) {

        List<DisplayMallGnb> gnbList = hanssemMallClient.findTemplateGnbV5List(
                HsMallType.REMODELING.name(),
                DisplayScreenType.MOBILE.getCode()).getGnbList();

        gnbList.forEach(gnbData -> {
            if (StringUtils.isNotBlank(gnbData.getDispTemplateNo())) {
                String gnbTemplateNo = gnbData.getDispTemplateNo();
                callDisplayApiAndGetDisplayData(allDataList, gnbTemplateNo, "");
            }
        });
    }

    private void setResultMapFromCategory(List<DisplayComponentWithDataResponse> allDataList) {

        String lCategoryTemplateNo = displayTemplateInfoDto.getLargeCategoryTemplateNo();
        String mCategoryTemplateNo = displayTemplateInfoDto.getMiddleCategoryTemplateNo();
        String sCategoryTemplateNo = displayTemplateInfoDto.getSmallCategoryTemplateNo();

        MallCategoryListVo mallCategoryList = hanssemMallClient.getHanssemMallCategoryList();

        List<MallCategoryListDetailInfoResponse> categories = CategoryMapper.INSTANCE.toMallCategoryListDetailInfoResponse(
                mallCategoryList.getCategoryList());

        categories.forEach(category -> {

            int categoryLevel = category.getCategoryLevel();
            String ctgNo = String.valueOf(category.getCategoryNo());

            if (categoryLevel == 2) {
                callDisplayApiAndGetDisplayData(allDataList, lCategoryTemplateNo, ctgNo);
            } else if (categoryLevel == 3) {
                callDisplayApiAndGetDisplayData(allDataList, mCategoryTemplateNo, ctgNo);
            } else if (categoryLevel == 4) {
                callDisplayApiAndGetDisplayData(allDataList, sCategoryTemplateNo, ctgNo);
            }

        });
    }

    private void callDisplayApiAndGetDisplayData(List<DisplayComponentWithDataResponse> allDataList, String templateNo, String ctgNo) {
        DisplayInfoRequest displayInfoRequest = DisplayInfoRequest.builder()
                .dispTemplateNo(templateNo)
                .ctgNo(ctgNo)
                .build();

        allDataList.addAll(this.findDisplayComponentInfo(displayInfoRequest, false).getDispComponentList());
    }


    @Override
    public void clearDisplayCacheAll() {

    }

    @Override
    public DisplayMenuResponse findMenuList(DisplayMenuRequest displayMenuRequest) {

        String viewType = displayMenuRequest.getViewType() == HsMallAllType.ALL ? null : displayMenuRequest.getViewType().getCode();
        HsMallMenuVo hsMallMenuVo = hanssemMallClient.findMenuList(
                displayMenuRequest.getTypeValue().getCode()
                , viewType);

        return DisplayMapper.INSTANCE.toDisplayMenuResponse(hsMallMenuVo);
    }
}
