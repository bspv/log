package com.bazzi.analysis.tests.log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPattern {
    public static void main(String[] args) {
//        String ss = "%d{yyyy-MM-dd HH:mm:ss.SSS}|%level|%thread|%X{ip}|%X{uri}|%X{platform}|%X{channel}|%X{clientVersion}|%X{deviceCode}|%X{model}|%X{userToken}|%X{token}|%X{serialNumber}|%logger{20}|%M|%msg%n";
//        String[] split = ss.split("\\|");
        String s1 = "%d{yyyy-MM-dd HH:mm:ss.SSS}";
        s1 = "%date{yyyy-MM-dd}";
        Pattern pattern = Pattern.compile("^%d(ate)?(\\{[yMdHmsS.: \\-]+})?$");
        Matcher matcher = pattern.matcher(s1);
        System.out.println("matcher.matches() = " + matcher.matches());

//        String s2 = "%X{IP}";
//        Pattern pattern2 = Pattern.compile("^%\\w+$");
//        Matcher matcher2 = pattern2.matcher(s2);
//        System.out.println("matcher2.matches() = " + matcher2.matches());
    }
}
