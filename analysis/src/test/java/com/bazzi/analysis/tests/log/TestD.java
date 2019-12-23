package com.bazzi.analysis.tests.log;

import com.bazzi.analysis.tests.util.LogPatternUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestD {
    public static void main(String[] args) {
        String log ="2019-01-07 16:08:06.164|INFO|http-nio-9001-exec-1|172.20.222.10||0|sfundrelease|1.0.0|721QACRL789CW|M6 Note|131e31277c374a2cb36bc91ae1c7dc28|027088a2cc494010a7dbeedfc623b3fd|b13fcbee-1339-4eb4-b6f3-2fa1e70acf3d|o.a.c.h.Http11Processor|log|Error parsing HTTP request header\n" +
                " Note: further occurrences of HTTP header parsing errors will be logged at DEBUG level.\n" +
                "java.lang.IllegalArgumentException: Invalid character found in method name. HTTP method names must be tokens\n" +
                "\tat org.apache.coyote.http11.Http11InputBuffer.parseRequestLine(Http11InputBuffer.java:428)\n" +
                "\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:687)\n" +
                "\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n" +
                "\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:790)\n" +
                "\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1468)\n" +
                "\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n" +
                "\tat java.lang.Thread.run(Thread.java:748)";

//        String log ="2019-01-07 16:24:37.395|INFO|http-nio-9001-exec-2|172.20.223.68||1|1|1.0.0|82AFA048-3956-4739-BAB4-5AAB1A6AA5EE|iPhone11,6|056ac5355de746ee8f4835decbf473fb|71bb7366ac5e4c9aae625f8aa2ba2616|2D701779-44FF-4F72-A458-A5A4EC3E2D96|c.h.a.a.FeignAspect|aroundFeign|Interactive--->MethodName:list, Module:finance-activity-integralMallAPI, Path:/api/coupon/list, Parameter:{\"sign\":\"E62F6670DBA13E494A0AFB51D27F8BB1\",\"couponStatus\":0,\"pageCurrent\":\"1\",\"pageSize\":\"10\"}, Header:{\"HX-PLATFORM\":\"1\",\"HX-CHANNEL\":\"1\",\"HX-CLIENT-VERSION\":\"1.0.0\",\"HX-DEVICE-CODE\":\"82AFA048-3956-4739-BAB4-5AAB1A6AA5EE\",\"HX-MODEL\":\"iPhone11,6\",\"HX-USER-TOKEN\":\"056ac5355de746ee8f4835decbf473fb\",\"HX-TOKEN\":\"71bb7366ac5e4c9aae625f8aa2ba2616\",\"HX-SERIAL-NUMBER\":\"2D701779-44FF-4F72-A458-A5A4EC3E2D96\",\"HX-IP\":\"172.20.223.68\"}, Result:{\"data\":{\"pageCurrent\":\"1\",\"pageData\":[{\"continueDay\":30,\"couponDescribe\":\"咕咚\",\"couponId\":3,\"couponName\":\"咕咚加倍卷\",\"couponRemark\":\"咕咚222\",\"couponShare\":\"666\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/cce999cda926490094ef2fd5c1d3a75b.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/09b41cd3f3d3427f9f2f1ee8a35b9a21.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/0ba6c3f264d34d28a0e31b4d9b77a358.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":17},{\"continueDay\":30,\"couponDescribe\":\"咕咚\",\"couponId\":3,\"couponName\":\"咕咚加倍卷\",\"couponRemark\":\"咕咚222\",\"couponShare\":\"666\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/cce999cda926490094ef2fd5c1d3a75b.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/09b41cd3f3d3427f9f2f1ee8a35b9a21.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/0ba6c3f264d34d28a0e31b4d9b77a358.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":16},{\"continueDay\":30,\"couponDescribe\":\"咕咚\",\"couponId\":3,\"couponName\":\"咕咚加倍卷\",\"couponRemark\":\"咕咚222\",\"couponShare\":\"666\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/cce999cda926490094ef2fd5c1d3a75b.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/09b41cd3f3d3427f9f2f1ee8a35b9a21.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/0ba6c3f264d34d28a0e31b4d9b77a358.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":15},{\"continueDay\":30,\"couponDescribe\":\"咕咚\",\"couponId\":3,\"couponName\":\"咕咚加倍卷\",\"couponRemark\":\"咕咚222\",\"couponShare\":\"666\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/cce999cda926490094ef2fd5c1d3a75b.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/09b41cd3f3d3427f9f2f1ee8a35b9a21.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/0ba6c3f264d34d28a0e31b4d9b77a358.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":14},{\"continueDay\":30,\"couponDescribe\":\"测试描述\",\"couponId\":4,\"couponName\":\"测试卡券\",\"couponRemark\":\"测试说明\",\"couponShare\":\"分享内容\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/a353f2edb7c44093b75b483fc3c99a0c.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/4b573782dbf44298994c1491e52e897f.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/1629c10661af4111af6cb8d9b0bb4c50.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":13},{\"continueDay\":30,\"couponDescribe\":\"测试描述\",\"couponId\":4,\"couponName\":\"测试卡券\",\"couponRemark\":\"测试说明\",\"couponShare\":\"分享内容\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/a353f2edb7c44093b75b483fc3c99a0c.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/4b573782dbf44298994c1491e52e897f.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/1629c10661af4111af6cb8d9b0bb4c50.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":12},{\"continueDay\":30,\"couponDescribe\":\"测试描述\",\"couponId\":4,\"couponName\":\"测试卡券\",\"couponRemark\":\"测试说明\",\"couponShare\":\"分享内容\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/a353f2edb7c44093b75b483fc3c99a0c.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/4b573782dbf44298994c1491e52e897f.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/1629c10661af4111af6cb8d9b0bb4c50.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":11},{\"continueDay\":30,\"couponDescribe\":\"测试描述\",\"couponId\":4,\"couponName\":\"测试卡券\",\"couponRemark\":\"测试说明\",\"couponShare\":\"分享内容\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/a353f2edb7c44093b75b483fc3c99a0c.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/4b573782dbf44298994c1491e52e897f.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/1629c10661af4111af6cb8d9b0bb4c50.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":10},{\"continueDay\":30,\"couponDescribe\":\"咕咚\",\"couponId\":3,\"couponName\":\"咕咚加倍卷\",\"couponRemark\":\"咕咚222\",\"couponShare\":\"666\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/cce999cda926490094ef2fd5c1d3a75b.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/09b41cd3f3d3427f9f2f1ee8a35b9a21.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/0ba6c3f264d34d28a0e31b4d9b77a358.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":9},{\"continueDay\":30,\"couponDescribe\":\"5555\",\"couponId\":2,\"couponName\":\"test   01\",\"couponRemark\":\"分享后可激活为期三天0.5%的加速值测试长度分享后可激活为期三天0.5%的加速值测试长度分享后可激活为期三天0.5%的加速值测试长度分享后可激活为期三天0.5%的加速值测试长度的加速值测试长度分享后1\",\"couponShare\":\"7771\",\"couponShareUrl\":\"http://static.test.hxlc.com/file/cce999cda926490094ef2fd5c1d3a75b.png\",\"couponThumbnailUrl\":\"http://static.test.hxlc.com/file/09b41cd3f3d3427f9f2f1ee8a35b9a21.png\",\"couponType\":1,\"couponUrl\":\"http://static.test.hxlc.com/file/0ba6c3f264d34d28a0e31b4d9b77a358.png\",\"couponValue\":110.0000,\"expirationDate\":\"2019-12-10\",\"startDate\":null,\"status\":0,\"ucId\":7}],\"pageSize\":\"10\",\"total\":11},\"status\":\"T\",\"code\":\"3000\",\"message\":\"success\"}, Time:131ms";

        String ss = "%d{yyyy-MM-dd HH:mm:ss.SSS}|%level|%thread|%X{ip}|%X{uri}|%X{platform}|%X{channel}|%X{clientVersion}|%X{deviceCode}|%X{model}|%X{userToken}|%X{token}|%X{serialNumber}|%logger{20}|%M|%msg%n";
        String d = "\\|";
        String s = LogPatternUtil.convertLogPatternToExtractPattern(ss, d);
        System.out.println("s = " + s);
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
