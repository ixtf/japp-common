package weixin;

import com.hengyi.japp.common.J;
import com.hengyi.japp.common.exception.WxException;
import com.hengyi.japp.common.weixin.QyAgent;
import com.hengyi.japp.common.weixin.QyClient;
import com.hengyi.japp.common.weixin.dto.QyDepartmentDTO;
import com.hengyi.japp.common.weixin.dto.QyUserDTO;
import com.qq.weixin.mp.aes.AesException;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/5/4.
 */
public class QyTest {
    private final static Properties p = new Properties();

    static {
        try {
            File file = new File("/home/jzb/weixin_config/hyqy.properties");
            p.load(new FileInputStream(file));
        } catch (IOException e) {

        }
//        p.put("corpid", "wx8d1fcf0d627bf7bb");
//        p.put("corpsecret", "ytXk7q14hszI-fpqxxAmJ5su97R1d9yrx8eyyIs8ely8EPOR33BXlVrRDrJFZqVW");
//        p.put("agentid", "13");
//        p.put("token", "m960GPqK4pXQv");
//        p.put("encodingaeskey", "vkoFXDgOsunF1NfkriipatPpHviDVgZdCSuJvH4zweY");
    }

//    @Test
    public void test() throws AesException, IOException, WxException {
        QyClient qy = J.weixinQy(p);
        QyAgent agent = J.weixinQyAgent(p);
        List<QyDepartmentDTO> dtos = qy.dep_list(null);
//        System.out.println(dtos);
        List<QyUserDTO> users = qy.user_list(1, 1, 0);

        //查看多部门的人员情况
        System.out.println("==========================查看多部门的人员情况==========================");
        users.stream()
                .filter(user -> CollectionUtils.emptyIfNull(user.getDepartment()).size() > 1)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((user, count) -> System.out.println(user.getName() + ":" + count));
        System.out.println("=========================查看 id 位数小于 8 位，怀疑不是员工号===========================");
        //查看 id 位数小于 8 位，怀疑不是员工号
        users.stream()
                .filter(user -> user.getUserid().length() < 8)
                .forEach(user -> System.out.println(user.getName() + ":" + user.getUserid()));

//        new QyMsgSend_text(agent, "test")
//                .addTouser("12000077")
//                .send();
    }
}
