package com.adaptionsoft.games.uglytrivia;

public class ConsoleReportEngine implements ReportEngine {
    public ConsoleReportEngine() {
    }

    @Override
    public void reportMessage(Object message) {
        System.out.println(message);
    }
}
