package com.songc.dto;

import lombok.Data;

/**
 * Created By @author songc
 * on 2017/11/18
 */
@Data
public class SingleRegionSignalDTO {
    double[] f;
    double[] f0;

    public SingleRegionSignalDTO(double[] f, double[] f0) {
        this.f = f;
        this.f0 = f0;
    }
}
