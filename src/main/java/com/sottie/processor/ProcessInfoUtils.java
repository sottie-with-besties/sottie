package com.sottie.processor;

public final class ProcessInfoUtils {
    public static ProcessInfo currentProcessInfo() {
        return ProcessInfoContextHolder.getCurrentProcessInfo();
    }

    public static String getCurrentProcessId() {
        ProcessInfo processInfo = ProcessInfoContextHolder.getCurrentProcessInfo();
        return processInfo != null ? processInfo.getSessionProcessId() : null;
    }

    public static String getCurrentRequestId() {
        ProcessInfo processInfo = ProcessInfoContextHolder.getCurrentProcessInfo();
        return processInfo != null ? processInfo.getRequestProcessId() : null;
    }

}
