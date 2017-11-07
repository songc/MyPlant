package com.songc.util;

import com.songc.dto.HbaseFileDTO;
import com.songc.entity.HbaseFile;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {
    public static List<HbaseFileDTO> convert(List<HbaseFile> hbaseFileList) {
        return hbaseFileList.stream()
                .map(HbaseFileDTO::new)
                .collect(Collectors.toList());
    }
}
