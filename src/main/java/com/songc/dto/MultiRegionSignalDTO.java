package com.songc.dto;

import lombok.Data;

import java.util.List;

/**
 * Created By @author songc
 * on 2017/11/18
 */
@Data
public class MultiRegionSignalDTO {
    List<double[]> f;
    List<double[]> f0;

    public MultiRegionSignalDTO(List<double[]> f, List<double[]> f0) {
        this.f = f;
        this.f0 = f0;
    }
}
