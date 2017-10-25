package com.songc.core.ap.detect.wave;

import java.util.Arrays;

/**
 * Created by songc on 2/14/2017.
 */
class FindAPPositionIndex {
    private Wave lowFilterWave;
    private Wave centralDiffWave;
    private Peaks peaksNoThreshold;
    private Peaks peaksWithThreshold;
    private int sampleFrequency;
    private double signalThreshold;
    private double noiseThreshold;

    FindAPPositionIndex(Augument augument) {
        this.lowFilterWave = augument.lowFilterWave;
        this.centralDiffWave = augument.centralDiffWave;
        this.peaksNoThreshold = augument.peaksNoThreshold;
        this.peaksWithThreshold = augument.peaksWithThreshold;
        this.sampleFrequency = augument.sampleFrequency;
        this.signalThreshold = augument.signalThreshold;
        this.noiseThreshold = augument.noiseThreshold;
    }

    APWavePositionIndex getAPWavePositionIndex() {

        int index1 = -1, index2;
        //注意数组越界问题
        //查找不设置阈值的波峰中第一个与设置阈值的波峰位置相等的位置，该位置之前的所有波峰都为噪声信号。
        int firstSignalPeaks = this.peaksWithThreshold.getLocs()[0];
        for (int i = 0; i < this.peaksNoThreshold.locs.length; i++) {
            if (this.peaksNoThreshold.locs[i] == firstSignalPeaks) {
                index1 = i;
                break;
            }
        }
        //注意数组下标问题原先 index1>3
        if (index1 > 2) {
            index2 = this.peaksNoThreshold.locs[index1 - 3];
            index1 = index1 / 2;
            index1 = this.peaksNoThreshold.locs[index1];
            //注意数组下标问题，原先 index1>1;
        } else if (index1 > 0) {
            index2 = this.peaksNoThreshold.locs[index1 - 1];
            index1 = this.peaksNoThreshold.locs[index1 / 2];
        } else {
            index2 = 2 * this.sampleFrequency;
            index1 = index1 / 2;
        }
        double baseline = 0, bias = 0, sum = 0;
        for (int i = index1 + 2; i < index2 - 3; i++) {
            sum += this.lowFilterWave.data[i];
        }
        baseline = sum / (index2 - 3 - index1 - 2);
        int len = this.peaksNoThreshold.locs.length;
        //k的初始化，原先为k=1，改为k=0
        int k = 0, leftPotion = -1, rightPotion = -1, leftPeak = -1, rightPeak = -1;
        int[] peaks = new int[len];
        int[] peaksLeft = new int[len];
        int[] peaksRight = new int[len];
        double[] rawPeaks = new double[len];
        double[] rawPeaksLeft = new double[len];
        double[] rawPeaksRight = new double[len];
        int j = 0;
        //注意数组下标 k<=augument,peaksWithThreshold.locs.length
        while (k < this.peaksWithThreshold.locs.length &&
                this.peaksWithThreshold.locs.length > 1) {
            //注意数组下标 原始k==1
            if (k == 0) {
                leftPotion = leftSearch(this.centralDiffWave.data, this.peaksWithThreshold.locs[0]);
                rightPotion = rightSearch(this.centralDiffWave.data, this.peaksWithThreshold.locs[0]);
                leftPeak = leftSearch(this.centralDiffWave.data, this.peaksWithThreshold.locs[1]);
                rightPeak = rightSearch(this.centralDiffWave.data, this.peaksWithThreshold.locs[1]);
                //注意数组下标 原始k=2
                k = 1;
            } else {
                leftPeak = leftSearch(this.centralDiffWave.data, this.peaksWithThreshold.locs[k]);
                rightPeak = rightSearch(this.centralDiffWave.data, this.peaksWithThreshold.locs[k]);
            }
            if (this.centralDiffWave.data[this.peaksWithThreshold.locs[k - 1]] *
                    this.centralDiffWave.data[this.peaksWithThreshold.locs[k]] < 0) {
                if (rightPotion == leftPeak || rightPotion + 1 == leftPeak) {
                    //写错了忘记+1了；这里好像有点问题&&两边是一样的
                    if (Math.abs(this.lowFilterWave.data[rightPotion + 1]) >=
                            Math.abs(this.lowFilterWave.data[rightPotion]) &&
                            Math.abs(this.lowFilterWave.data[rightPotion + 1]) >=
                                    Math.abs(this.lowFilterWave.data[rightPotion])) {
                        peaks[j] = rightPotion + 1;
                    } else {
                        peaks[j] = rightPotion;
                    }
                    rawPeaks[j] = this.lowFilterWave.data[rightPotion];
                    peaksLeft[j] = leftPotion;
                    rawPeaksLeft[j] = this.lowFilterWave.data[leftPotion];
                    peaksRight[j] = rightPeak;
                    rawPeaksRight[j] = this.lowFilterWave.data[rightPeak];
                    j++;
                }
            } else {
                //这里修改了下
                int index = -1;
                for (int tmp :
                        this.peaksNoThreshold.locs) {
                    if (tmp > this.peaksWithThreshold.locs[k - 1] & tmp < this.peaksWithThreshold.locs[k]) {
                        index++;
                    }
                }
                if (index != -1 && (this.peaksWithThreshold.locs[k] - this.peaksWithThreshold.locs[k - 1]) <= 1 * this.sampleFrequency) {
                    leftPotion = leftPeak;
                    rightPotion = rightPeak;
                }
            }

            k++;
//            j++;
            leftPotion = leftPeak;
            rightPotion = rightPeak;
        }
        peaks = Arrays.copyOfRange(peaks, 0, j);
        peaksLeft = Arrays.copyOfRange(peaksLeft, 0, j);
        peaksRight = Arrays.copyOfRange(peaksRight, 0, j);
        rawPeaks = Arrays.copyOfRange(rawPeaks, 0, j);
        rawPeaksLeft = Arrays.copyOfRange(rawPeaksLeft, 0, j);
        rawPeaksRight = Arrays.copyOfRange(rawPeaksRight, 0, j);
        //需要考虑下peaksleft几个数组的长度，初始初始化是设置的是最大的长度。
        int jj = 0;
        //注意数组越界问题 原先jj<peaksLeft.length-1;
        // int/2 = floor(int/2)matlab, int/2+1 = ceil(int/2)
        while (peaksLeft.length > 0 && jj < (peaksLeft.length - 1)) {
            if (peaksRight[jj] > peaksLeft[jj + 1]) {
                int num = (peaksRight[jj] - peaksLeft[jj + 1]) / 2;
                peaksRight[jj] = peaksRight[jj] - (num + 1);
                rawPeaksRight[jj] = this.lowFilterWave.data[peaksRight[jj]];
                peaksLeft[jj + 1] = peaksLeft[jj + 1] + num;
                rawPeaksLeft[jj + 1] = this.lowFilterWave.data[peaksLeft[jj + 1]];
            }
            jj++;
        }
        double[] peaksValue = new double[len];
        double[] peaksLeftValue = new double[len];
        double[] peaksRightValue = new double[len];
        int[] peaksLoc = new int[len];
        int[] peaksLeftLoc = new int[len];
        int[] peaksRightLoc = new int[len];
        int ci, len3 = peaksLeft.length;
        int index3 = 0;
        double camplitude;
        int pi;
        int numpeak = 0;
        //数组的下标问题
        while (len3 > 1 && index3 < (len3 - 1)) {
            camplitude = Math.abs(rawPeaksLeft[index3] - rawPeaks[index3]);
            pi = index3;
            ci = index3;
            while (index3 < (len3 - 1) && (peaksRight[index3] + 1 == peaksLeft[index3 + 1] ||
                    peaksRight[index3] == peaksLeft[index3 + 1])) {
                if (camplitude < Math.abs(rawPeaksLeft[ci] - rawPeaks[index3 + 1])) {
                    pi = index3 + 1;
                    camplitude = rawPeaksLeft[ci] - rawPeaks[index3 + 1];
                }
                index3++;
            }
            if (ci < index3 && camplitude > 5) {
                peaksLeftLoc[numpeak] = peaksLeft[ci];
                peaksLeftValue[numpeak] = rawPeaksLeft[ci];
                peaksRightLoc[numpeak] = peaksRight[index3];
                peaksRightValue[numpeak] = rawPeaks[index3];
                peaksLoc[numpeak] = peaks[pi];
                peaksValue[numpeak] = rawPeaks[pi];
                numpeak++;
            } else if (ci == index3 && camplitude > 5) {
                peaksLeftLoc[numpeak] = peaksLeft[index3];
                peaksLeftValue[numpeak] = rawPeaksLeft[index3];
                peaksRightLoc[numpeak] = peaksRight[index3];
                peaksRightValue[numpeak] = rawPeaks[index3];
                peaksLoc[numpeak] = peaks[index3];
                peaksValue[numpeak] = rawPeaks[index3];
                numpeak++;
            }
            index3++;
        }
        //注意数组下标问题， 原先index3==len3；
        if (len3 == 1 || index3 == len3 - 1) {
            camplitude = Math.abs(rawPeaksLeft[len3 - 1] - rawPeaks[len3 - 1]);
            if (camplitude > 5) {
                //原先都是len3改为len3-1
                peaksLeftLoc[numpeak] = peaksLeft[len3 - 1];
                peaksLeftValue[numpeak] = rawPeaksLeft[len3 - 1];
                peaksRightLoc[numpeak] = peaksRight[len3 - 1];
                peaksRightValue[numpeak] = rawPeaks[len3 - 1];
                peaksLoc[numpeak] = peaks[len3 - 1];
                peaksValue[numpeak] = rawPeaks[len3 - 1];
                numpeak++;
            }
        }
        int[] actionPotentialLeft = Arrays.copyOfRange(peaksLeftLoc, 0, numpeak);
        int[] actionPotentialRight = Arrays.copyOfRange(peaksRightLoc, 0, numpeak);
        int[] actionPotentialPeaks = Arrays.copyOfRange(peaksLoc, 0, numpeak);
        return new APWavePositionIndex(actionPotentialLeft, actionPotentialPeaks, actionPotentialRight);
    }

    public int leftSearch(double[] signal, int peakLoc) {
        int left;
        while (peakLoc > 0) {
            if (Math.abs(signal[peakLoc]) == 0 ||
                    signal[peakLoc] * signal[peakLoc - 1] <= 0) {
                break;
            } else {
                peakLoc--;
            }
        }
        //注意数组下标问题 原先 peakLoc>1;
        if (peakLoc > 0 && Math.abs(signal[peakLoc]) > 0 && signal[peakLoc - 1] == 0) {
            peakLoc--;
        }
        left = peakLoc;
        return left;
    }

    public int rightSearch(double[] signal, int peakLoc) {
        int right;
        //注意数组越界问题
        while (peakLoc < (signal.length - 1)) {
            if (Math.abs(signal[peakLoc]) == 0 || signal[peakLoc] * signal[peakLoc + 1] <= 0) {
                break;
            } else {
                peakLoc++;
            }
        }
        //注意数组越界问题，MATLAB的数组是从1开始的
        if (peakLoc > 0 && peakLoc < (signal.length - 1) &&
                Math.abs(signal[peakLoc]) > 0 && signal[peakLoc + 1] == 0) {
            peakLoc++;
        }
        right = peakLoc;
        return right;
    }

    public static class Augument {

        Peaks peaksNoThreshold;
        Peaks peaksWithThreshold;
        Wave lowFilterWave;
        Wave centralDiffWave;
        int sampleFrequency;
        double signalThreshold;
        double noiseThreshold;

        public Augument(Peaks allPeaks, Peaks signalPeaks, Wave lowFilterWave, Wave centralDiffWave, int sampleFrequency, double signalThreshold, double noiseThreshold) {
            this.peaksNoThreshold = allPeaks;
            this.peaksWithThreshold = signalPeaks;
            this.centralDiffWave = centralDiffWave;
            this.sampleFrequency = sampleFrequency;
            this.signalThreshold = signalThreshold;
            this.noiseThreshold = noiseThreshold;
            this.lowFilterWave = lowFilterWave;
        }
    }
}
