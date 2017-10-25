package com.songc.core.ap;

import com.songc.core.ap.detect.wave.APInRawWave;
import com.songc.core.ap.detect.wave.APWaveExtract;
import com.songc.core.ap.detect.wave.RawWave;
import com.songc.core.ap.detect.wave.Wave;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songc on 3/1/2017.
 */
public class APExtractService {
    private Double[][] data;
    private int rate;

    public APExtractService(Double[][] data, int rate) {
        this.data = data;
        this.rate = rate;
    }

    /**
     * 初始化数据，将字符串数组的数据转换为
     * list<wave>的数据
     *
     * @return
     */
    private List<Wave> init() {
        List<Wave> waveList = new ArrayList<>();
        for (Double[] doubles : this.data) {
            double[] temp = new double[doubles.length];
            int i = 0;
            for (Double aDouble : doubles) {
                temp[i++] = aDouble;
            }
            waveList.add(new RawWave(temp, rate));
        }
        return waveList;
    }

    public List<List<APInRawWave>> getAllAPInfo() {
        List<Wave> rawWaves = this.init();
        List<List<APInRawWave>> result = new ArrayList<List<APInRawWave>>();
        for (Wave rawWave :
                rawWaves) {
            APWaveExtract apWaveExtract = new APWaveExtract();
            result.add(apWaveExtract.getAPInRawWave(rawWave));
        }
        return result;
    }
}
