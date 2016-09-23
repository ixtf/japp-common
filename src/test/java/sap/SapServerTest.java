package sap;

import com.hengyi.japp.common.J;
import com.hengyi.japp.common.sap.SapClient;

/**
 * Created by jzb on 16-7-19.
 */
public class SapServerTest {
    private static SapClient sap = J.sap();

    public static void main(String[] args) {
        sap.registerFunctionHandler(new TestFunctionHandler());
        while (true) {
        }
    }
}
