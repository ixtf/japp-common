package lamba;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jzb on 16-5-7.
 */
public class MapCollect {
    static List<Test> tests = Lists.newArrayList();

    static {
        tests.add(new Test("1", "test1"));
        tests.add(new Test("2", "test2"));
    }

    public static void main(String[] args) {
        tests.stream().collect(Collectors.toMap(test -> test.id, test -> test));
    }

    static class Test {
        String id;
        String name;

        public Test(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
