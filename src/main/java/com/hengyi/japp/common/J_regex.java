package com.hengyi.japp.common;

import java.util.regex.Pattern;

/**
 * Created by jzb on 15-12-5.
 */
public class J_regex {
    public static final Pattern imageP = Pattern.compile("^.+[^\\.\\s]\\.(jpg|jpeg|gif|png|bmp)$", Pattern.CASE_INSENSITIVE);
    public static final Pattern numberP = Pattern.compile("^\\d+$");
    //中文字符
    public static final Pattern zwP = Pattern.compile("([\u4e00-\u9fa5]+)");

    static final boolean isImage(String fileName) {
        return imageP.matcher(fileName).matches();
    }

    static boolean isNumberString(CharSequence cs) {
        return numberP.matcher(cs).matches();
    }

    /**
     * 是否全中文
     */
    public static boolean isZwString(CharSequence cs) {
        return zwP.matcher(cs).matches();
    }

    /**
     * 是否包含中文
     */
    static boolean isContainZw(CharSequence cs) {
        return zwP.matcher(cs).find();
    }
}
