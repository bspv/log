package com.bazzi.analysis.util;

import com.bazzi.analysis.bean.LogDetail;

public final class LogNameUtil {

    private static final String WINDOWS = "windows";
    private static final String WINDOWS_SPLIT = "\\";
    private static final String LINUX_SPLIT = "/";
    private static final String POINT = ".";

    /**
     * 从logDetail中根据platform来解析source获取日志文件名
     *
     * @param logDetail 日志详情
     * @return 日志文件名
     */
    public static String getLogName(LogDetail logDetail) {
        return logDetail.getFileName();
    }

}
