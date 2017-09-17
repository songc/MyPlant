package com.songc.util;

import org.apache.commons.lang.StringUtils;

import java.time.Instant;

public class HbaseUtil {
    private static Long HF_ID = 0L;
    public static String ConvertRowKey(Long parentId) {
        return StringUtils.reverse(String.format("%016d", parentId))
                + (Long.MAX_VALUE - Instant.now().toEpochMilli())
                + String.format("%016d", HF_ID++);
    }
}
