package com.hanssem.remodeling.content.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utility {

    public static StringJoiner stringWithBlank() {
        return new StringJoiner(" ");
    }

    private static final String CHAR_SET = "UTF-8";
    public static final String AREA = "Asia/Seoul";
    public static final String VIEW_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final DateTimeFormatter dateTimeFormatterForExcel = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter getDateTimeFormatter() {
        return getDateTimeFormatter("");
    }

    public static DateTimeFormatter getDateTimeFormatter(final String format) {
        return StringUtils.isNotBlank(format) ? DateTimeFormatter.ofPattern(format) : DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    public static String getDateTimeFormatter(final LocalDateTime localDateTime){
        return localDateTime != null ? localDateTime.atZone(ZoneId.of("Asia/Seoul")).format(dateTimeFormatter) : null;
    }

    public static String getDateTimeFormatterForExcel(final LocalDateTime localDateTime){
        return localDateTime != null ? localDateTime.atZone(ZoneId.of("Asia/Seoul")).format(dateTimeFormatterForExcel) : null;
    }

    public static String decodeWithTrim(final String str) {
        String value = "";
        try {
            value = trim(URLDecoder.decode(str, CHAR_SET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static String decode(final String str) {
        String value = "";
        try {
            value = URLDecoder.decode(str, CHAR_SET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static String trim(final String str) {
        return str.replaceAll("\\p{Z}", "");
    }

    public static String replaceXSSEncoding(final String source) {
        return source.replaceAll("<script", "&lt;script").replaceAll("script>", "script&gt;");
    }

    public static String replaceXSSDecoding(final String source) {
        return source.replaceAll("&lt;script", "<script").replaceAll("script&gt;", "script>");
    }

    public static byte[] sha256(final String value) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(value.getBytes());

        return md.digest();
    }

    public static String bytesToHex(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static <E extends List> boolean isListEmpty(final E list) {
        if (list == null) {
            return true;
        }
        return list.isEmpty();
    }

    public static boolean regExMatcher(final String regEx, String text) {
        if (isStrBlank(text)) {
            return false;
        }

        return Pattern.compile(regEx).matcher(text).find();
    }

    public static boolean regExMatcherWithTrim(final String regEx, String text) {
        if (isStrBlank(text)) {
            return false;
        }

        return regExMatcher(regEx, trim(text));
    }

    public static boolean isStrBlank(final String text) {
        return StringUtils.isBlank(text);
    }

    public static <E extends Enum<E>> E getValueByName(Class<E> e, String name){

        name = name.replaceAll("\\p{Z}","");

        Map<String, E> enumToMap = Collections.unmodifiableMap(
            Stream.of(e.getEnumConstants()).collect(Collectors.toMap(E::name, Function.identity())));

        return enumToMap.get(name);
    }

    public static String getCurrentDate(String type) {
        switch (type.toLowerCase()) {
            case "year":
                return String.format("%d", LocalDate.now().getYear());
            case "month":
                return LocalDate.now().getMonthValue() < 10 ? String.format("%s%d", "0", LocalDate.now().getMonthValue()) : String.format("%d", LocalDate.now().getMonthValue());
            case "day":
                return LocalDate.now().getDayOfMonth() < 10 ? String.format("%s%d", "0", LocalDate.now().getDayOfMonth()) : String.format("%d", LocalDate.now().getDayOfMonth());
            default:
                return "";
        }
    }

    public static String getCurrentTime(String formatter) {
        if (StringUtils.isBlank(formatter)) {
            formatter = "yyyy-MM-dd HH:mm:ss";
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(formatter));
    }

    public static String getFileExtension(final String value) {
        return value.substring(value.lastIndexOf(".") + 1);
    }

    public static LocalDateTime stringConvertLocalDateTime(String dateTime, DateTimeFormatter dateTimeFormatter){
        return dateTime==null?null:LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static String localDateTimeConvertString(final LocalDateTime date) {
        if(date == null){
            return "";
        }
        return date.atOffset(ZoneId.of(AREA).getRules().getOffset(date)).format(DateTimeFormatter.ofPattern(VIEW_DATE_FORMAT));
    }

    public static StringJoiner newStringJoiner(final String delimiter) {
        return new StringJoiner(delimiter);
    }

    public static String getJoinString(final Object... values) {
        return String.format("%s".repeat(values.length), values);
    }

    /**
     * List 중복 제거 함수
     * (ex)
     * List<ModifyPortfolioSpaceTagRequest> distinctTags = target.stream()
     *            .filter(new DistinctByKey<>(ModifyPortfolioSpaceTagRequest::getTag)::filterByKey)
     *             .collect(Collectors.toList());
     */
    public static class  DistinctByKey<T> {
        private Function<T, Object> function;
        private Set<Object> seenObjects;

        private DistinctByKey(Function<T, Object> function) {
            this.function = function;
            this.seenObjects = new HashSet<>();
        }

        public boolean filterByKey(T t) {
            return seenObjects.add(function.apply(t));
        }
    }
}
