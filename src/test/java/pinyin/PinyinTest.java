package pinyin;

import com.google.common.collect.ImmutableSet;
import com.hengyi.japp.common.J;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Administrator on 2016/5/3.
 */
public class PinyinTest {
    private final static String word = "单单单";
    private final static Set<String> result;

    static {
        ImmutableSet.Builder builder = ImmutableSet.builder();
        builder.add("ccc");
        builder.add("css");
        builder.add("ssc");
        builder.add("sds");
        builder.add("dcd");
        builder.add("ccd");
        builder.add("ssd");
        builder.add("dcc");
        builder.add("dss");
        builder.add("ccs");
        builder.add("scc");
        builder.add("sss");
        builder.add("scd");
        builder.add("dcs");
        builder.add("scs");
        builder.add("ddc");
        builder.add("cdd");
        builder.add("cdc");
        builder.add("ddd");
        builder.add("csc");
        builder.add("dds");
        builder.add("sdd");
        builder.add("cds");
        builder.add("sdc");
        builder.add("dsd");
        builder.add("dsc");
        builder.add("csd");
        result = builder.build();
    }

    @Test
    public void test() {
        Set<String> set = J.firstSpell(word);
        Assert.assertEquals(set.size(), result.size());
        set.forEach(s -> Assert.assertTrue(result.contains(s)));
    }
}
