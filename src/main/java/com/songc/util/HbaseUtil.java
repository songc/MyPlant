package com.songc.util;

import com.songc.entity.HbaseFile;
import org.apache.commons.lang.StringUtils;

import java.time.Instant;

public class HbaseUtil {
    public static String ConvertRowKey(Long parentId) {
        return StringUtils.reverse(String.format("%016d", parentId))
                + (Long.MAX_VALUE - Instant.now().toEpochMilli())
                + String.format("%016d", HbaseFile.HF_ID);
    }
}
