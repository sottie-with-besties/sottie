package com.sottie.processor;

public class ProcessInfoContextHolder {
    private static final ThreadLocal<ProcessInfo> currentProcessInfo = new ThreadLocal<>();

    public static void setCurrentProcessInfo(ProcessInfo processInfo) {
        currentProcessInfo.set(processInfo);
    }

    public static ProcessInfo getCurrentProcessInfo() {
        return currentProcessInfo.get();
    }

    public static void clear() {
        currentProcessInfo.remove();
    }
}
