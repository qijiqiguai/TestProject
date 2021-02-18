package code.qijiqiguai.java.tools;


import java.util.HashMap;
import java.util.Map;

public class Test2 {
    static String input = "log4j.logger.org.apache.ibatis=DEBUG,cliSqlDailyRolling\n" +
            "log4j.logger.org.apache.ibatis.common.jdbc.SimpleDataSource=DEBUG,cliSqlDailyRolling\n" +
            "log4j.logger.org.apache.ibatis.jdbc.ScriptRunner=DEBUG,cliSqlDailyRolling\n" +
            "log4j.logger.org.apache.ibatis.sqlmap.engine.impl.SqlMapClientDelegate=DEBUG,cliSqlDailyRolling\n" +
            "\n" +
            "##\n" +
            "#log4j.rootLogger=OFF\n" +
            "log4j.logger.com.netease.ysf=DEBUG,cliDailyRolling,${log.console}\n" +
            "\n" +
            "log4j.additivity.com.netease.ysf.device.dao=false\n" +
            "log4j.logger.com.netease.ysf.device.dao=DEBUG,cliSqlDailyRolling,${log.console}\n" +
            "\n" +
            "log4j.additivity.com.netease.ysf.tenant.repository.db.dao=false\n" +
            "log4j.logger.com.netease.ysf.tenant.repository.db.dao=INFO,cliSqlDailyRolling,${log.console}\n" +
            "\n" +
            "log4j.logger.org.springframework=ERROR,cliDailyRolling,${log.console}\n" +
            "log4j.logger.com.alibaba=ERROR,cliDailyRolling,${log.console}\n" +
            "log4j.logger.com.codahale.metrics.ysf=INFO,cliMetricsDailyRolling,${log.console}";
    static Map<String, String> appenderMap = new HashMap<>();
    public static void main(String[] args) {
        appenderMap.put("cliDailyRolling", "AppRolling");
        appenderMap.put("cliSqlDailyRolling", "SqlDailyRolling");
        appenderMap.put("cliMetricsDailyRolling", "MetricsDailyRolling");

        String[] lines = input.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String oneLine = lines[i];
            processOne(oneLine);
        }
    }

    private static void processOne(String oneLine) {
        String prefix = "log4j.logger.";
        try {
            if( oneLine.startsWith(prefix) ){
                String[] info = oneLine.replaceAll(prefix, "").split("=");
                String clazz = info[0].trim();
                String level = info[1].split(",")[0].trim();
                String appender = info[1].split(",")[1].trim();
                generateOne(clazz, appender, level);
            }else {
                System.out.println("Skip:" + oneLine);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + oneLine + " By: " + e.getMessage());
        }
    }

    private static void generateOne(String clazz, String appender, String level) {
        String ref = appenderMap.get(appender);
        String one =
                "<AsyncLogger name=\""+ clazz +"\" level=\""+level+"\" additivity=\"false\" includeLocation=\"true\">\n" +
                "   <AppenderRef ref=\""+ ref +"\"/>\n" +
                "</AsyncLogger>";
        System.out.println(one);
    }
}
