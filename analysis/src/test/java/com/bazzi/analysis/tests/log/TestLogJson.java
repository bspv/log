package com.bazzi.analysis.tests.log;

public class TestLogJson {

    public static void main(String[] args) {
//        String json = "{\"@timestamp\":\"2018-12-24T09:05:06.045Z\",\n" +
//                "\"@metadata\":{\"beat\":\"filebeat\",\"type\":\"doc\",\"version\":\"6.5.1\",\"topic\":\"topic_api_log_dev\"},\n" +
//                "\"input\":{\"type\":\"log\"},\n" +
//                "\"prospector\":{\"type\":\"log\"},\n" +
//                "\"beat\":{\"version\":\"6.5.1\",\"name\":\"GetOn-PC\",\"hostname\":\"GetOn-PC\"},\n" +
//                "\"host\":{\"name\":\"GetOn-PC\",\n" +
//                "        \"architecture\":\"x86_64\",\n" +
//                "        \"os\":{\"version\":\"10.0\",\n" +
//                "            \"family\":\"windows\",\n" +
//                "            \"build\":\"17134.472\",\n" +
//                "            \"platform\":\"windows\"},\n" +
//                "        \"id\":\"319a6b0b-5bdd-47b4-8a8a-4169122eb723\"},\n" +
//                "\"source\":\"d:\\\\export\\\\data\\\\logs\\\\info\\\\finance-api-gateway-info.log\",\n" +
//                "\"offset\":23016,\n" +
//                "\"message\":\"2018-12-24 17:05:04.886|INFO|restartedMain|||||||||||o.s.w.s.m.m.a.RequestMappingHandlerMapping|register|Mapped \\\"{[/risk/hasRisk],methods=[POST]}\\\" onto public com.hxlc.api.generic.Result\\u003ccom.hxlc.api.vo.response.HasRiskResponseVO\\u003e com.hxlc.api.controller.RiskAssessmentController.hasRisk(com.hxlc.api.vo.request.DefaultReqVO)\"}\n";
//        LogDetail ld = JsonUtil.parseObject(json, LogDetail.class);
//        System.out.println("ld = " + ld);
//        String message = ld.getMessage();
//        String[] split = message.split("\\|");
//        System.out.println("split = " + split);

        String ss = " 2014-05-14 23:24:47 15752 InnoDB: 128 rollback segment(s) are active";
        String[] arr = ss.split("(?<timestamp>\\S+ \\S+) (?<pid>\\S+) (?<message>.*)");
        System.out.println("arr = " + arr.length);
    }
}
