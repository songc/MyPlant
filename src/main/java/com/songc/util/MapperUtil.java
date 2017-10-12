package com.songc.util;

import com.songc.dto.HbaseFileDTO;
import com.songc.entity.HbaseFile;

import java.util.ArrayList;
import java.util.List;

public class MapperUtil {
    public static List<HbaseFileDTO> convert(List<HbaseFile> hbaseFileList) {
        List<HbaseFileDTO> result = new ArrayList<>();
        for (HbaseFile hbaseFile : hbaseFileList) {
            result.add(new HbaseFileDTO(hbaseFile));
        }
        return result;
    }
}
