package weixin;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.hengyi.japp.common.J;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.MpClient;
import com.hengyi.japp.common.weixin.WeixinClient;
import com.hengyi.japp.common.weixin.msg.MpMsgTpl;
import com.qq.weixin.mp.aes.AesException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by jzb on 16-5-9.
 */
public class MpTest {
    private static final Properties p = new Properties();
    //测试
    private static final String pPath = "/home/jzb/weixin_config/wjs.properties";
    //正式
//    private static final String pPath="/home/jzb/weixin_config/wjh.properties";

    static {
        try {
            File file = new File(pPath);
            p.load(new FileInputStream(file));
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) throws AesException, IOException, WxException {
        MpClient mp = WeixinClient.instance(MpClient.class, p);
        JsonNode result = new MpMsgTpl(mp, "oohBQwjH5QPqyNVFzf54MTMSBV0Y", "12Yf7boNoZHL65pyeznIfJv7IuHn1us_eNyO1boNeX0", "")
                .data("first", "test")
                .data("keyword1", "test")
                .data("keyword2", keyword2())
                .send();
        System.out.println(result);
    }

    private static String keyword2() {
        List<String> list = Lists.newArrayList("sadfas.jpg", "dfsfs.gif");
        return list.stream()
                .filter(name -> J.isImage(name))
                .map(name -> new StringBuilder("<a href=\"")
                        .append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3902a712d038c298&redirect_uri=http%3a%2f%2fwww.wjh001.com%2fexecution%2foauth%2fweixin%3fnext%3dhttp%3a%2f%2fwww.wjh001.com%2fexecution%2fupload%2fDRSYbwN7KP7rrnq5Bt7Gv.PNG&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect")
                        .append("\">").append(name).append("</a>"))
                .collect(Collectors.joining("\n"));
    }
}
