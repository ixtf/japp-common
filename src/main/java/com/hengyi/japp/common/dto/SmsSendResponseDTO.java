package com.hengyi.japp.common.dto;

import com.hengyi.japp.common.sap.annotation.SapConvertField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import static org.apache.commons.beanutils.BeanUtils.setProperty;
import static org.apache.commons.lang3.StringUtils.split;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SmsSendResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SmsSendResponseDTO.class);
    @SapConvertField("RESP")
    private String _resp;
    private String result;
    private String description;
    private String taskid;
    private String faillist;

    public SmsSendResponseDTO(String _resp) {
        super();
        this._resp = _resp;
        for (String pair : split(_resp, "&")) {
            String[] s = split(pair, "=");
            if (s.length < 2) {
                continue;
            }
            String key = s[0];
            String value = s[1];
            try {
                setProperty(this, key, value);
            } catch (Exception e) {
                log.error(_resp);
                log.error("短信发送的Response解析出错！key=" + key + ",value=" + value, e);
            }
        }
    }

    public SmsSendResponseDTO() {
        super();
    }

    public boolean isSuccess() {
        return "0".equals(result);
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = StringUtils.deleteWhitespace(result);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaillist() {
        return faillist;
    }

    public void setFaillist(String faillist) {
        this.faillist = faillist;
    }

    public String get_resp() {
        return _resp;
    }
}
