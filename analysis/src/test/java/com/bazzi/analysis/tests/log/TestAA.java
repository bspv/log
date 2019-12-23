package com.bazzi.analysis.tests.log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAA {
    public static void main(String[] args) {
//        String ss = "2014-05-14 23:24:47 15752 InnoDB: 128 rollback segment(s) are active";
//        Pattern pattern = Pattern.compile("(?<timestamp>\\S+ \\S+) (?<pid>\\S+) (?<message>.*)");
//        Matcher matcher = pattern.matcher(ss);
//        boolean b = matcher.find();
//        System.out.printf("\n matcher.group('timestamp') value:%s", matcher.group("timestamp"));
//        System.out.printf("\n matcher.group('pid') value:%s", matcher.group("pid"));
//        System.out.printf("\n matcher.group('message') value:%s", matcher.group("message"));

        String regex = "(?<timestamp>\\S+ \\S+) (?<pid>\\S+) \\[(?<loglevel>\\S+)] (?<message>.*)";
        Pattern pattern = Pattern.compile(regex);

        String ss = "2014-05-14 23:24:47 15752 [Note] InnoDB: 128 rollback segment(s) are active";
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));

        }
    }
}
