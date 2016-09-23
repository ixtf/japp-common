package lamba;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by jzb on 16-5-6.
 */
public class MapCompute {
    private static Map<String, List<String>> map = Maps.newConcurrentMap();

    public static void main(String[] args) {
        map.put("1", Lists.newArrayList("11", "111"));

        map.compute("1", (key, value) -> {
            System.out.println(key);
            System.out.println(value);
            return Lists.newArrayList("22", "222");
        });
        System.out.println(map.get("1"));

        map.compute("2", (key, value) -> {
            System.out.println(key);
            System.out.println(value);
            return Lists.newArrayList("33", "333");
        });
        System.out.println(map.get("2"));
    }
}
