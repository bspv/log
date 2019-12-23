package com.bazzi.manager.util;

import java.math.BigDecimal;

public final class ByteConvertUtil {

    /**
     * byte转TB，四舍五入，精确到小数点后两位
     *
     * @param byteNum 字节数
     * @return TB值
     */
    public static double byteToTB(String byteNum) {
        return byteConvert(byteNum, 4);
    }

    /**
     * byte转GB，四舍五入，精确到小数点后两位
     *
     * @param byteNum 字节数
     * @return GB值
     */
    public static double byteToGB(String byteNum) {
        return byteConvert(byteNum, 3);
    }

    /**
     * byte转MB，四舍五入，精确到小数点后两位
     *
     * @param byteNum 字节数
     * @return MB值
     */
    public static double byteToMB(String byteNum) {
        return byteConvert(byteNum, 2);
    }

    /**
     * byte转KB，四舍五入，精确到小数点后两位
     *
     * @param byteNum 字节数
     * @return KB值
     */
    public static double byteToKB(String byteNum) {
        return byteConvert(byteNum, 1);
    }

    /**
     * 根据num来转换byte到KB、MB、GB、TB等
     *
     * @param byteNum 字节数
     * @param num     指数，代表1024的num次方
     * @return 结果
     */
    private static double byteConvert(String byteNum, int num) {
        if (byteNum == null || "".equals(byteNum))
            return -1;
        BigDecimal result = new BigDecimal(byteNum).
                divide(BigDecimal.valueOf(Math.pow(1024, num)), 2, BigDecimal.ROUND_DOWN);
        return result.doubleValue();
    }
}
