package com.hengyi.japp.common.weixin.dto;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by jzb on 16-5-4.
 */
public class QyUserDTO extends QyUserDTO_simple {
    private String position;
    private String mobile;
    private String gender;
    private String email;
    private String weixinid;
    private String avatar;
    /**
     * 关注状态: 1=已关注，2=已冻结，4=未关注
     */
    private String status;
    /**
     * 扩展属性
     * "extattr": {"attrs":[{"name":"爱好","value":"旅游"},{"name":"卡号","value":"1234567234"}]}
     */
    private JsonNode extattr;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixinid() {
        return weixinid;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JsonNode getExtattr() {
        return extattr;
    }

    public void setExtattr(JsonNode extattr) {
        this.extattr = extattr;
    }
}
