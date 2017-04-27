package com.hengyi.japp.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.uuid.Generators;
import com.google.zxing.WriterException;
import com.hengyi.japp.common.dto.SmsSendDTO;
import com.hengyi.japp.common.dto.SmsSendResponseDTO;
import com.hengyi.japp.common.interfaces.sms.soap.SmsPortType;
import com.hengyi.japp.common.interfaces.sms.soap.Sms_Service;
import com.hengyi.japp.common.sap.DestinationType;
import com.hengyi.japp.common.sap.SapClient;
import com.hengyi.japp.common.toHtml.ToHtml;
import com.hengyi.japp.common.toHtml.ToHtmlBuilder;
import com.hengyi.japp.common.toHtml.ToHtmlType;
import com.hengyi.japp.common.weixin.*;
import com.qq.weixin.mp.aes.AesException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.hengyi.japp.common.Constant.MAPPER;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by jzb on 15-12-5.
 */
public class J {
//    public static final String uuid() {
//        return Generators.timeBasedGenerator().generate().toString();
//    }

//    public static final String uuid58(UUID uuid) {
//        return J_codec.uuid64(Generators.timeBasedGenerator().generate());
//    }

    public static <T> String sqlIn(Collection<T> collection, Function<T, String> fn) {
        return collection.stream()
                .parallel()
                .map(fn)
                .distinct()
                .collect(Collectors.joining(","));
    }

    public static <T> String sqlIn(Collection<T> collection) {
        return sqlIn(collection, Objects::toString);
    }

    public static String sqlLike(String s) {
        return "'%" + StringUtils.defaultString(s) + "%'";
    }

    public static SmsSendResponseDTO sendSms(SmsSendDTO dto) {
        final String SMS_SPCODE = StringUtils.defaultIfBlank(System.getProperty("SMS_SPCODE"), "217025");
        final String SMS_LOGINNAME = StringUtils.defaultIfBlank(System.getProperty("SMS_LOGINNAME"), "zj_hysh");
        final String SMS_PASSWORD = StringUtils.defaultIfBlank(System.getProperty("SMS_PASSWORD"), "hysh1234");
        SmsPortType smsPortType = new Sms_Service().getSmsHttpPort();
        String res = smsPortType.sms(SMS_SPCODE, SMS_LOGINNAME, SMS_PASSWORD,
                dto.getContent(), dto.getPhonesAsString(), dto.getSerialNumber(), dto.getScheduleTime(), dto.getSendCheckType(),
                null, null, null);
        return new SmsSendResponseDTO(res);
    }

    public static final String nameUuid58(String... names) {
        String name = String.join(",", names);
        return J_codec.uuid58(UUID.nameUUIDFromBytes(name.getBytes(UTF_8)));
    }

    public static final String uuid58() {
        return J_codec.uuid58(Generators.timeBasedGenerator().generate());
    }

    public static final String password() {
        return password("123456");
    }

    public static final String password(final String password) {
        try {
            return J_codec.password(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException ex) {
            //绝不会发生的异常
            throw new RuntimeException(ex);
        }
    }

    public static final boolean checkPassword(final String encryptPassword, final String password) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        return J_codec.checkPassword(encryptPassword, password);
    }

    public static String toJson(Object o) {
        return o == null ? null : J_json.writeValueAsString(o);
    }

    public static ObjectNode toObjectNode(Object o) {
        return o == null ? null : MAPPER.convertValue(o, ObjectNode.class);
    }

    public static ArrayNode toArrayNode(Collection o) {
        ArrayNode result = MAPPER.createArrayNode();
        if (CollectionUtils.isNotEmpty(o))
            o.forEach(it -> result.add(toObjectNode(it)));
        return result;
    }

    public static <T> T fromJson(String o, Class<T> clazz) {
        return o == null ? null : J_json.readValue(o, clazz);
    }

    public static <T> T fromJson(JsonParser o, Class<T> clazz) {
        return o == null ? null : J_json.readValue(o, clazz);
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return MAPPER.convertValue(fromValue, toValueType);
    }

    public static final <T> T getCdiBean(Class<T> clazz, Annotation... qualifiers) {
        return J_cdi.getBean(clazz, qualifiers);
    }

    public static final void cdiInject(Object o) {
        J_cdi.inject(J_cdi.getBeanManager(), o);
    }

    public static final SapClient sap() {
        return SapClient.instance(DestinationType.PRO);
    }

    public static final SapClient sapDev() {
        return SapClient.instance(DestinationType.DEV);
    }

    public static final SapClient sapEq() {
        return SapClient.instance(DestinationType.EQ);
    }

    public static final QyClient weixinQy(Properties p) throws AesException {
        return WeixinClient.instance(QyClient.class, p);
    }

    public static final QyAgent weixinQyAgent(Properties p) throws AesException {
        return WeixinClient.instance(QyAgent.class, p);
    }

    public static final MpClient weixinMp(Properties p) throws AesException {
        return WeixinClient.instance(MpClient.class, p);
    }

    public static final OpenClient weixinOpen(Properties p) throws AesException {
        return WeixinClient.instance(OpenClient.class, p);
    }

    public static final Set<String> firstSpell(final String cs) {
        return J_pinyin.getFirstSpell(cs);
    }

    public static String deletePrefZero(String s) {
        if (StringUtils.isBlank(s)) {
            return s;
        }
        if (s.startsWith("0")) {
            return deletePrefZero(s.substring(1));
        }
        return s;
    }

    public static final LocalDateTime localDateTime(Date date) {
        return date == null ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static final LocalDate localDate(Date date) {
        return date == null ? null : localDateTime(date).toLocalDate();
    }

    public static final LocalTime localTime(Date date) {
        return date == null ? null : localDateTime(date).toLocalTime();
    }

    public static final Date date(LocalDateTime ldt) {
        return ldt == null ? null : Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static final Date date(LocalDate ld) {
        return ld == null ? null : date(ld.atStartOfDay());
    }

    public static final Date date(LocalTime lt) {
        return lt == null ? null : date(lt.atDate(LocalDate.of(1977, Month.JANUARY, 1)));
    }

    public static final String formatDate(Date date) {
        return date == null ? null : DateFormatUtils.ISO_DATE_FORMAT.format(date);
    }

    public static final String formatTime(Date date) {
        return date == null ? null : FastDateFormat.getInstance("HH:mm").format(date);
    }

    public static final String formatDateTime(Date date) {
        return date == null ? null : FastDateFormat.getInstance("yyyy-MM-dd HH:mm").format(date);
    }

    public static final boolean isImage(String fileName) {
        return J_regex.isImage(fileName);
    }

    public static final boolean isNumberString(CharSequence cs) {
        return J_regex.isNumberString(cs);
    }

    public static final boolean isContainZw(CharSequence cs) {
        return J_regex.isContainZw(cs);
    }

    public static final BufferedImage qrcode(String content) throws WriterException {
        return J_image.qrcode(content);
    }

    public static final BufferedImage resizeImage(File file, int newWidth) throws IOException {
        return J_image.resize(file, newWidth, 1);
    }

    public static final String cas_url(String url) {
        try {
            return "http://cas.hengyi.com:8080/login?service=" + URLEncoder.encode(url, UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static final ToHtmlBuilder file2Html() throws Exception {
        return new ToHtmlBuilder();
    }
}
