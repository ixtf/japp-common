package weixin;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.junit.Test;

/**
 * Created by Administrator on 2016/5/4.
 */
public class WeixinTest {
    private String msg_signature = "a44b92baee4b1ba8a4e5b378844ff9a39d74473d";
    private String timestamp = "1462296338";
    private String nonce = "801718006";
    private String echostr = "3LWTTG+JAc70C/YrLuGF0Wg1gL3pGOaA6+U7DkApqUWOQkUPFoKfpmGL74sE89B4XbMpRWAG4PaL+f63pHJT+A==";

    @Test
    public void test() throws AesException {
        WXBizMsgCrypt w = new WXBizMsgCrypt("m960GPqK4pXQv", "vkoFXDgOsunF1NfkriipatPpHviDVgZdCSuJvH4zweY", "wx8d1fcf0d627bf7bb");
        String s = w.verifyUrl(msg_signature, timestamp, nonce, echostr);
        System.out.println(s);
    }
}
