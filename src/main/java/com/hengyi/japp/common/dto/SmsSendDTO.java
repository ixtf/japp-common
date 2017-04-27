package com.hengyi.japp.common.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsSendDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotBlank
    private String content;
    @NotNull
    @Size(min = 1)
    private Collection<String> phones;
    /*
     * 流水号，20位数字，唯一
     */
    private String serialNumber;
    /*
     * 预约发送时间，格式:yyyyMMddhhmmss,如‘20090901010101’，立即发送请填空
     */
    @XmlSchemaType(name = "dateTime")
    private Date sendDate;
    /*
     * 1 --- 提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出
     * 
     * 不为1 或该参数不存在 --- 提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出
     */
    private String sendCheckType;

    public SmsSendDTO() {
        super();
    }

    public SmsSendDTO(String content, Collection phones) {
        super();
        setContent(content);
        setPhones(phones);
    }

    public SmsSendDTO(String content, String... phones) {
        this(content, Sets.newHashSet(phones));
    }

    public static final String getPhonesAsString(Collection<String> phones) {
        String result = Arrays.toString(phones.toArray());
        result = StringUtils.deleteWhitespace(result);
        return result.substring(1, result.length() - 1);
    }

    public String getSendCheckType() {
        return sendCheckType == null ? "1" : sendCheckType;
    }

    public void setSendCheckType(String sendCheckType) {
        this.sendCheckType = sendCheckType;
    }

    public String getScheduleTime() {
        return sendDate == null ? null : new SimpleDateFormat("yyyyMMddhhmmss")
                .format(sendDate);
    }

    public String getPhonesAsString() {
        return getPhonesAsString(phones);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Collection<String> getPhones() {
        return phones;
    }

    public void setPhones(Collection<String> phones) {
        Set<String> _set = Sets.newHashSet(phones);
        this.phones = Lists.newArrayList(_set);
    }
}
