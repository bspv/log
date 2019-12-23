package com.bazzi.analysis.tests.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogPatternUtil {
    //日期，不支持时区
    private static final String F_DATE = "^%d(ate)?(\\{[yMdHmsS.: \\-]+})?$";
    private static final String G_DATE = "(?<date>%s)";
    private static final String G_DEFAULT_DATE = "(?<date>[\\S\\s]*)";
    private static final Pattern P_DATE = Pattern.compile(F_DATE);
    //level，匹配%highlight(%-5level)、%-5level、%level
    private static final String F_LEVEL = "^(%\\w+\\()?%(-\\d)?(p|le|level)(\\))?$";
    private static final String G_LEVEL = "(?<level>\\w{4,5})";
    private static final Pattern P_LEVEL = Pattern.compile(F_LEVEL);
    //logger
    private static final String F_LOGGER = "^(%\\w+\\()?%(c|lo|logger)(\\{\\d+})?(\\))?$";
    private static final String G_LOGGER = "(?<logger>[\\S\\s]*)";
    private static final Pattern P_LOGGER = Pattern.compile(F_LOGGER);
    //消息字段
    private static final String F_MSG = "^(%\\w+\\()?%(m|msg|message)(%n)?(\\))?$";
    private static final String G_MSG = "(?<msg>[\\S\\s]*)";
    private static final Pattern P_MSG = Pattern.compile(F_MSG);

    //MDC字段
    private static final String F_MDC = "^(%\\w+\\()?%(X|mdc)\\{\\w+}(\\))?$";
    private static final String G_MDC = "(?<%s>[\\S\\s]*)";
    private static final Pattern P_MDC = Pattern.compile(F_MDC);
    //%aaa、%aaa{bbb}字段，如thread、M等
    private static final String F_COMMON = "^(%\\w+\\()?%\\w+(\\{\\d+})?(\\))?$";
    private static final String G_COMMON = "(?<%s>[\\S\\s]*)";
    private static final Pattern P_COMMON = Pattern.compile(F_COMMON);

    //提取括号()中的内容
    private static final String F_EXTRACT_KH = "(?<=\\()(.+?)(?=\\))";
    private static final Pattern P_EXTRACT_KH = Pattern.compile(F_EXTRACT_KH);
    //提取括号{}中的内容
    private static final String F_EXTRACT_DKH = "(?<=\\{)(.+?)(?=\\})";
    private static final Pattern P_EXTRACT_DKH = Pattern.compile(F_EXTRACT_DKH);
    //提取括号%{之间的内容
    private static final String F_EXTRACT_BK = "(?<=%)(.+?)(?=\\{)";
    private static final Pattern P_EXTRACT_BK = Pattern.compile(F_EXTRACT_BK);

    public static String convertLogPatternToExtractPattern(String logPattern, String delimiter) {
        if (logPattern == null || "".equals(logPattern) || delimiter == null || "".equals(delimiter))
            return null;
        String[] arr = logPattern.split(delimiter);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String group = getGroupPattern(arr[i]);
            sb.append(group);
            if (i != arr.length - 1)
                sb.append(delimiter);
        }
        return sb.toString();
    }

    private static String getGroupPattern(String s) {
        if (P_DATE.matcher(s).matches()) {
            Matcher matcher = P_EXTRACT_DKH.matcher(s);
            if (!matcher.find()) {
                return G_DEFAULT_DATE;
            } else {
                String format = matcher.group();
                //yyyy-MM-dd HH:mm:ss.SSS
                format = format.replace("yyyy", "\\d{4}");
                format = format.replace("MM", "\\d{2}");
                format = format.replace("dd", "\\d{2}");
                format = format.replace("HH", "\\d{2}");
                format = format.replace("mm", "\\d{2}");
                format = format.replace("ss", "\\d{2}");
                format = format.replace("SSS", "\\d{3}");
                return String.format(G_DATE, format);
            }
        }

        if (P_LEVEL.matcher(s).matches()) {
            return G_LEVEL;
        }

        if (P_LOGGER.matcher(s).matches()) {
            return G_LOGGER;
        }

        if (P_MSG.matcher(s).matches()) {
            return G_MSG;
        }

        if (P_MDC.matcher(s).matches()) {
            Matcher m1 = P_EXTRACT_KH.matcher(s);
            String f = m1.find() ? m1.group() : s;

            Matcher m2 = P_EXTRACT_DKH.matcher(f);
            String mdcName = m2.find() ? m2.group() : null;
            return String.format(G_MDC, mdcName);
        }

        if (P_COMMON.matcher(s).matches()) {
            Matcher m1 = P_EXTRACT_KH.matcher(s);
            String f = m1.find() ? m1.group() : s;

            Matcher m2 = P_EXTRACT_BK.matcher(f);
            String fieldName = m2.find() ? m2.group() : f.substring(f.indexOf("%") + 1);
            return String.format(G_COMMON, fieldName);
        }

        return null;
    }

    public static void main(String[] args) {
        String ss = "%d{yyyy-MM-dd HH:mm:ss.SSS}|%level|%thread|%X{ip}|%X{uri}|%X{platform}|%X{channel}|%X{clientVersion}|%X{deviceCode}|%X{model}|%X{userToken}|%X{token}|%X{serialNumber}|%logger{20}|%M|%msg%n";
//        String ss = "%d{yyyy-MM-dd HH:mm:ss.SSS} | %level | %thread | %X{ip} | %X{uri} | %X{platform} | %X{channel} | %X{clientVersion} | %X{deviceCode} | %X{model} | %X{userToken} | %X{token} | %X{serialNumber} | %logger{20} | %M | %msg%n";
//        String d = " \\| ";
        String d = "\\|";
        String s = convertLogPatternToExtractPattern(ss, d);
        System.out.println("s = " + s);
        String log = "2019-01-02 18:01:24.684|INFO|Thread-20|||||||||||o.s.s.c.ThreadPoolTaskScheduler|shutdown|Shutting down ExecutorService 'taskScheduler'";
//        String log = "2019-01-02 18:01:24.684 | INFO | Thread-20 |  |  |  |  |  |  |  |  |  |  | o.s.s.c.ThreadPoolTaskScheduler | shutdown | Shutting down ExecutorService 'taskScheduler'";
        Pattern pattern = Pattern.compile(s);
        Matcher matcher = pattern.matcher(log);
        if (matcher.find()) {
            System.out.println("matcher.matches() = " + matcher.group(0));
            System.out.println("matcher.matches() = " + matcher.group(1));
            System.out.println("matcher.matches() = " + matcher.group(2));
            System.out.println("matcher.matches() = " + matcher.group(3));
            System.out.println("matcher.matches() = " + matcher.group(4));
            System.out.println("matcher.matches() = " + matcher.group(5));
            System.out.println("matcher.matches() = " + matcher.group(6));
            System.out.println("matcher.matches() = " + matcher.group(7));
            System.out.println("matcher.matches() = " + matcher.group(8));
            System.out.println("matcher.matches() = " + matcher.group(9));
            System.out.println("matcher.matches() = " + matcher.group(10));
            System.out.println("matcher.matches() = " + matcher.group(11));
            System.out.println("matcher.matches() = " + matcher.group(12));
            System.out.println("matcher.matches() = " + matcher.group(13));
            System.out.println("matcher.matches() = " + matcher.group(14));
            System.out.println("matcher.matches() = " + matcher.group(15));
            System.out.println("matcher.matches() = " + matcher.group(16));
        }
    }

}
