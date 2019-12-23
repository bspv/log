package com.bazzi.manager.util;

import com.bazzi.manager.model.ProjectField;

import java.util.List;
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

    /**
     * 获取捕获组正则
     *
     * @param logPattern 日志正则
     * @param delimiter  分隔符
     * @param list       经过提取的正则信息
     * @return 捕获组正则
     */
    public static String getCaptureRegular(String logPattern, String delimiter, List<ProjectField> list) {
        if (logPattern == null || "".equals(logPattern) || delimiter == null || "".equals(delimiter) || list == null)
            return null;
        delimiter = Pattern.quote(delimiter);
        String[] arr = logPattern.split(delimiter);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            ProjectField pf = new ProjectField();
            pf.setFieldIdx(i + 1);
            pf.setFieldLogRegular(arr[i]);
            getGroupPattern(pf);
            sb.append(pf.getFieldRegular());
            if (i != arr.length - 1)
                sb.append(delimiter);
            list.add(pf);
        }
        return sb.toString();
    }

    /**
     * 获取捕获正则
     *
     * @param pf 字段信息
     */
    private static void getGroupPattern(ProjectField pf) {
        String s = pf.getFieldLogRegular();
        if (P_DATE.matcher(s).matches()) {
            Matcher matcher = P_EXTRACT_DKH.matcher(s);
            String fieldRegular;
            if (!matcher.find()) {
                fieldRegular = G_DEFAULT_DATE;
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
                fieldRegular = String.format(G_DATE, format);
            }
            pf.setFieldName("date");
            pf.setFieldRegular(fieldRegular);
            return;
        }

        if (P_LEVEL.matcher(s).matches()) {
            pf.setFieldName("level");
            pf.setFieldRegular(G_LEVEL);
            return;
        }

        if (P_LOGGER.matcher(s).matches()) {
            pf.setFieldName("logger");
            pf.setFieldRegular(G_LOGGER);
            return;
        }

        if (P_MSG.matcher(s).matches()) {
            pf.setFieldName("msg");
            pf.setFieldRegular(G_MSG);
            return;
        }

        if (P_MDC.matcher(s).matches()) {
            Matcher m1 = P_EXTRACT_KH.matcher(s);
            String f = m1.find() ? m1.group() : s;

            Matcher m2 = P_EXTRACT_DKH.matcher(f);
            String mdcName = m2.find() ? m2.group() : null;
            pf.setFieldName(mdcName);
            pf.setFieldRegular(String.format(G_MDC, mdcName));
            return;
        }

        if (P_COMMON.matcher(s).matches()) {
            Matcher m1 = P_EXTRACT_KH.matcher(s);
            String f = m1.find() ? m1.group() : s;

            Matcher m2 = P_EXTRACT_BK.matcher(f);
            String fieldName = m2.find() ? m2.group() : f.substring(f.indexOf("%") + 1);

            pf.setFieldName(fieldName);
            pf.setFieldRegular(String.format(G_COMMON, fieldName));
            return;
        }
    }

}
