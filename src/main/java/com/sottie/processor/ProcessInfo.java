package com.sottie.processor;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessInfo implements Closeable {
    private String sessionProcessId;
    private String requestProcessId;
    private AtomicInteger processSeq = new AtomicInteger(1);

    public ProcessInfo() {}

    public ProcessInfo(String sessionProcessId, String requestProcessId) {
        this.sessionProcessId = sessionProcessId;
        this.requestProcessId = requestProcessId;
    }

    public Integer getProcessSeq() {
        return this.processSeq.get();
    }

    public Integer increaseProcessSeq() {
        return this.processSeq.incrementAndGet();
    }

    public static ProcessInfo create() {
        String sessionProcessId = processUniqueId();
        String requestProcessId = processUniqueId();

        return new ProcessInfo(sessionProcessId, requestProcessId);
    }

    @Override
    public void close() throws IOException {
        ProcessInfoContextHolder.clear();
    }

    private static String processUniqueId() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer uuidBytes = ByteBuffer.wrap(new byte[16]);
        uuidBytes.putLong(uuid.getMostSignificantBits());
        uuidBytes.putLong(uuid.getLeastSignificantBits());

        return System.currentTimeMillis() + new String(uuidBytes.array());
    }

    public String getSessionProcessId() {
        return this.sessionProcessId;
    }

    public String getRequestProcessId() {
        return this.requestProcessId;
    }
}
