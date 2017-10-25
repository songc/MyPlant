package com.songc.core.ap.detect.wave;

import com.songc.core.ap.util.MathMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by songc on 2/14/2017.
 */
public class APWaveExtract {
    private double TH0; //设定噪声阀值。
    private int pointNum; //确定差分的方法；
    private int scale; //波形数据的小数位数，

    public APWaveExtract() {
        this.TH0 = 0.1;
        this.pointNum = 5;
        this.scale = 3;
    }

    public APWavePositionIndex findAPWavePositionIndex(Wave rawWave) {
        int mark = 0;
        double noiseTHL = 0, signalTHH = 0;
        double sumNoise = 0;
        int timesNoise = 0;
        Wave lowFilterWave = new LowFilter(rawWave).getLowFilterWave();
        if (MathMethods.maxInArrays(lowFilterWave.data) == 0) {
            lowFilterWave = new MinusWave(lowFilterWave);
        }
        Wave centralDiffWave = new CentralDiff(pointNum).getCentralDiffWave(lowFilterWave);
        if (MathMethods.maxInArrays(centralDiffWave.data) == 0) {
            centralDiffWave = new MinusWave(centralDiffWave);
        }
        centralDiffWave = new DivideWave(centralDiffWave);
        centralDiffWave = new AccuracyWave(centralDiffWave, scale);
        Peaks peaksNoThreshold = new FindPeaks(0).getPeaks(centralDiffWave);
        Peaks peaksThreshold = new FindPeaks(TH0).getPeaks(centralDiffWave);
        double[] signalAfterDifference = centralDiffWave.data;
        for (int k = 0; k < peaksNoThreshold.locs.length; k++) {
            //注意数组长度
            if (k < peaksThreshold.locs.length && signalAfterDifference[peaksThreshold.locs[k]] < 0) {
                peaksThreshold.pks[k] = signalAfterDifference[peaksThreshold.locs[k]];
            }
            if (signalAfterDifference[peaksNoThreshold.locs[k]] < 0) {
                peaksNoThreshold.pks[k] = signalAfterDifference[peaksNoThreshold.locs[k]];
            }
            if (Math.abs(peaksNoThreshold.pks[k]) <= TH0) {
                sumNoise += Math.abs(peaksNoThreshold.pks[k]);
                timesNoise++;
            }
        }
        if (mark == 0) {
            noiseTHL = sumNoise / timesNoise;
            signalTHH = MathMethods.arraysMean(MathMethods.arraysAbs(peaksThreshold.pks));
            TH0 = noiseTHL;
            mark = 1;
        } else {
            noiseTHL = 0.75 * noiseTHL + 0.25 * (sumNoise / timesNoise);
            signalTHH = 0.75 * signalTHH + 0.25 * MathMethods.arraysMean(MathMethods.arraysAbs(peaksThreshold.pks));
            TH0 = noiseTHL;
        }
        if (TH0 > 0.05 && TH0 < 0.1) {
            TH0 = 0.05;
        } else if (TH0 >= 0.1) {
            TH0 = noiseTHL * 0.6;
        }
        FindAPPositionIndex.Augument augument = new FindAPPositionIndex.Augument(peaksNoThreshold, peaksThreshold, lowFilterWave, centralDiffWave, rawWave.rate, signalTHH, noiseTHL);
        return new FindAPPositionIndex(augument).getAPWavePositionIndex();
    }

    public List<Wave> findAPWave(Wave wave) {
        List<Wave> allAPWave = new ArrayList<Wave>();
        APWavePositionIndex APIndex = this.findAPWavePositionIndex(wave);
        int APNum = APIndex.leftIndex.length;
        for (int i = 0; i < APNum; i++) {
            allAPWave.add(new APWave(Arrays.copyOfRange(wave.data, APIndex.leftIndex[i], APIndex.rightIndex[i]), wave.rate));
        }
        return allAPWave;
    }

    //获取RawWave中提取出APwave的详细信息，起始位置和波分位置等信息。
    public List<APInRawWave> getAPInRawWave(Wave wave) {
        List<APInRawWave> result = new ArrayList<APInRawWave>();
        APWavePositionIndex apIndex = this.findAPWavePositionIndex(wave);
        int APNum = apIndex.leftIndex.length;
        for (int i = 0; i < APNum; i++) {
            APInRawWave apInRawWave = new APInRawWave();
            apInRawWave.setData(Arrays.copyOfRange(wave.data, apIndex.leftIndex[i], apIndex.rightIndex[i]));
            apInRawWave.setStartIndex(apIndex.leftIndex[i]);
            apInRawWave.setPeakIndex(apIndex.peaksIndex[i]);
            apInRawWave.setEndIndex(apIndex.rightIndex[i]);
            result.add(apInRawWave);
        }
        return result;
    }
}



