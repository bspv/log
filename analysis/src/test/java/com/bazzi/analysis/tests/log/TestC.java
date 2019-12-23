package com.bazzi.analysis.tests.log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestC {

    public static void main(String[] args) {
        String s = "%magenta(%thread)";
//        s = "%highlight(%-5level)";
//        s = "%-5level";
//        Pattern pattern = Pattern.compile("^(%\\w+\\()?%\\w+(\\{\\d+})?(\\))?$");
        Pattern pattern = Pattern.compile("^(%\\w+\\()?%(-\\d)?(p|le|level)(\\))?$");
        Matcher matcher = pattern.matcher(s);
        System.out.println("matcher.matches() = " + matcher.matches());
    }
}
