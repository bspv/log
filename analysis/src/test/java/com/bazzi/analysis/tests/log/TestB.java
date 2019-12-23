package com.bazzi.analysis.tests.log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestB {
    public static void main(String[] args) {

        String s1 = "%date{";
        s1 = "%d{yyyy-MM-dd HH:mm:ss.SSS}";
//        Pattern pattern = Pattern.compile("^?:(%d(ate)?\\()\\{[yMdHmsS.: \\-]+}?:\\)?$");
//        Pattern pattern = Pattern.compile("^?:(%d(ate)?\\()\\{[yMdHmsS.: \\-]+}?:\\)$");
//        Pattern pattern = Pattern.compile("\\{?<=[yMdHmsS.: \\-]+}");

        Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
//        Pattern pattern = Pattern.compile("(?<=%)(.+?)(?=\\{)");
        Matcher matcher = pattern.matcher(s1);
        if (matcher.find()) {
            System.out.println("matcher.matches() = " + matcher.group());
//            System.out.println("matcher.matches() = " + matcher.group("format"));
        }

    }
}
