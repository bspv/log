package com.bazzi.analysis.tests.log;

import com.bazzi.analysis.tests.util.LogPatternUtil;

public class TestPrePattern {
    public static void main(String[] args) {
//        String ss = "%d{yyyy-MM-dd HH:mm:ss.SSS} | %highlight(%-5level) | %magenta(%thread) | %cyan(%logger{20}) | %msg%n";
        String ss = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n";
        String d = " \\| ";
        String s = LogPatternUtil.convertLogPatternToExtractPattern(ss, d);
        System.out.println("s = " + s);
    }
}
