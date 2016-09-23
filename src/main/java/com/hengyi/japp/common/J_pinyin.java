package com.hengyi.japp.common;

import com.google.common.collect.Sets;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by jzb on 15-12-5.
 */
public class J_pinyin {
    public static final Pattern zwP = Pattern.compile("([\u4e00-\u9fa5]+)");
    /**
     * 两个集合进行排列组合
     */
    private static final BinaryOperator<Set<String>> combinSetF = (set1, set2) -> {
        Set<String> _r = Sets.newHashSetWithExpectedSize(set1.size() * set2.size());
        set1.forEach(a -> set2.forEach(b -> _r.add(a + b)));
        return _r;
    };

    static Set<String> getFirstSpell(String cs) {
        Validate.notBlank(cs);
        Optional<Set<String>> result = cs.chars()
                .mapToObj(i -> {
                    char c = (char) i;
                    /**
                     * 单字全拼的数组，可能有多音字
                     */
                    String[] fullPys = PinyinHelper.toHanyuPinyinStringArray(c);
                    /**
                     * 把单字拼音数组中的首字母拿出，组成 set 集合
                     */
                    return Arrays.stream(fullPys).map(s -> s.substring(0, 1))
                            .collect(Collectors.toSet());
                }).reduce(combinSetF);
        return result.orElse(null);
    }

    static Set<String> getFullSpell(String cs) {
        Validate.notBlank(cs);
        Optional<Set<String>> result = cs.chars()
                .mapToObj(i -> {
                    char c = (char) i;
                    /**
                     * 单字全拼的数组，可能有多音字
                     */
                    String[] fullPys = PinyinHelper.toHanyuPinyinStringArray(c);
                    Set<String> _r = Sets.newHashSet(fullPys);
                    return _r;
                })
                .reduce(combinSetF);
        return result.orElse(null);
    }
}
