package password;

import com.hengyi.japp.common.J;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class PasswordTest {
    @Test
    public void test() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < 10; ++t) {
            sb.append(UUID.randomUUID().toString());
        }
        long s = System.currentTimeMillis();
        String p = J.password(sb.toString());
        long e = System.currentTimeMillis();
        System.out.println("time=" + (e - s));

        s = System.currentTimeMillis();
        boolean b = J.checkPassword(p, sb.toString());
        e = System.currentTimeMillis();
        System.out.println("time=" + (e - s));

        Assert.assertTrue(b);
    }
}
