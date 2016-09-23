package sap;

import com.hengyi.japp.common.J;
import com.hengyi.japp.common.sap.Rfc;
import com.hengyi.japp.common.sap.SapClient;
import com.sap.conn.jco.JCoException;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by jzb on 16-7-5.
 */
public class ConvertTest {
    private SapClient sap = J.sap();

    @Test
    public void test1() throws NoSuchFieldException, JCoException {
        final String s = "testtesttesttest";
        Rfc rfc = sap.rfc("STFC_CONNECTION")
                .setImport("REQUTEXT", s)
                .exe();
        String ECHOTEXT = rfc.getExport("ECHOTEXT");
        Assert.assertTrue(Objects.equals(s, ECHOTEXT));
        String RESPTEXT = rfc.getExport("RESPTEXT");
        System.out.println(RESPTEXT);

        booleanTest();
    }

    private void booleanTest() throws NoSuchFieldException {
        Field beanField = TestDTO.class.getDeclaredField("b1");
        Class beanFieldType = beanField.getType();
        Assert.assertTrue(beanFieldType.isAssignableFrom(boolean.class));
        Assert.assertFalse(beanFieldType.isAssignableFrom(Boolean.class));

        beanField = TestDTO.class.getDeclaredField("b2");
        beanFieldType = beanField.getType();
        Assert.assertFalse(beanFieldType.isAssignableFrom(boolean.class));
        Assert.assertTrue(beanFieldType.isAssignableFrom(Boolean.class));
    }

    class TestDTO {
        private boolean b1;
        private Boolean b2;

        public boolean isB1() {
            return b1;
        }

        public void setB1(boolean b1) {
            this.b1 = b1;
        }

        public Boolean getB2() {
            return b2;
        }

        public void setB2(Boolean b2) {
            this.b2 = b2;
        }
    }
}
