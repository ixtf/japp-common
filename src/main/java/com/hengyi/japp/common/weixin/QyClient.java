package com.hengyi.japp.common.weixin;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.dto.QyDepartmentDTO;
import com.hengyi.japp.common.weixin.dto.QyUserDTO;
import com.hengyi.japp.common.weixin.dto.QyUserDTO_simple;
import com.qq.weixin.mp.aes.AesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.hengyi.japp.common.Constant.MAPPER;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by jzb on 16-1-16.
 */
public class QyClient extends WeixinClient {
    final String corpid;
    final String corpsecret;
    private final Map<Integer, QyAgent> agents = Maps.newConcurrentMap();

    QyClient(String corpid, String corpsecret) {
        Validate.notBlank(corpid);
        Validate.notBlank(corpsecret);
        this.corpid = corpid;
        this.corpsecret = corpsecret;
    }

    String access_token() throws IOException, WxException {
        JsonNode node = get(Urls.gettoken, ImmutableMap.of("corpid", corpid, "corpsecret", corpsecret));
        return node.get("access_token").asText();
    }

    /**
     * 微信网页授权,构造跳转链接
     */
    public String authorizeUrl(String redirect_uri, String state) {
        Validate.notBlank(redirect_uri);
        try {
            return MessageFormat.format(MpClient.Urls.authorizeUrlTpl, corpid,
                    URLEncoder.encode(redirect_uri, UTF_8.name()),
                    StringUtils.defaultString(state, "123"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String authorizeUrl(String redirect_uri) {
        return authorizeUrl(redirect_uri, null);
    }

    /**
     * 获取部门列表
     *
     * @param id 部门id,获取指定部门及其下的子部门,id为空则获取所有部门
     */
    public List<QyDepartmentDTO> dep_list(String id) throws IOException, WxException {
        JsonNode node = get(Urls.dep_list, ImmutableMap.of("access_token", access_token(), "id", StringUtils.defaultString(id)));
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, QyDepartmentDTO.class);
        return MAPPER.convertValue(node.get("department"), javaType);
    }

    /**
     * 获取部门成员
     *
     * @param department_id 获取的部门id
     * @param fetch_child   1/0：是否递归获取子部门下面的成员
     * @param status        0-获取全部成员，1-获取已关注成员列表，2-获取禁用成员列表，4-获取未关注成员列表。status可叠加，未填写则默认为4
     */
    public List<QyUserDTO_simple> user_simplelist(int department_id, int fetch_child, int status) throws IOException, WxException {
        JsonNode node = get(Urls.user_simplelist, ImmutableMap.of("access_token", access_token(), "department_id", "" + department_id, "fetch_child", "" + fetch_child, "status", "" + status));
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, QyUserDTO_simple.class);
        return MAPPER.convertValue(node.get("userlist"), javaType);
    }

    /**
     * 获取部门成员(详情)
     *
     * @param department_id 获取的部门id
     * @param fetch_child   1/0：是否递归获取子部门下面的成员
     * @param status        0-获取全部成员，1-获取已关注成员列表，2-获取禁用成员列表，4-获取未关注成员列表。status可叠加，未填写则默认为4
     */
    public List<QyUserDTO> user_list(int department_id, int fetch_child, int status) throws IOException, WxException {
        JsonNode node = get(Urls.user_list, ImmutableMap.of("access_token", access_token(), "department_id", "" + department_id, "fetch_child", "" + fetch_child, "status", "" + status));
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, QyUserDTO.class);
        return MAPPER.convertValue(node.get("userlist"), javaType);
    }

    synchronized QyAgent agent(Properties p) throws AesException {
        int agentid = Integer.parseInt(p.getProperty("agentid"));
        QyAgent result = agents.get(agentid);
        if (result == null) {
            result = new QyAgent(this, agentid, p.getProperty("token"), p.getProperty("encodingaeskey"));
            agents.put(agentid, result);
        }
        return result;
    }

    public static final class Urls {
        public static final String gettoken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        public static final String authorizeUrlTpl = "https://open.weixin.qq.com/connect/oauth2/authorize?"
                + "appid={0}&"
                + "redirect_uri={1}&"
                + "response_type=code&"
                + "scope=snsapi_base&"
                + "state={2}"
                + "#wechat_redirect";
        public static final String dep_list = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
        public static final String user_simplelist = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";
        public static final String user_list = "https://qyapi.weixin.qq.com/cgi-bin/user/list";
    }
}
