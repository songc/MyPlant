package com.songc.core.ap.detect.wave;

import com.songc.core.ap.util.MathMethods;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * Created by songc on 2/14/2017.
 */
public abstract class Wave {
    protected int rate;
    protected double[] data;

    public double corrValue(Wave apWave) {
        double[] data = apWave.getData();
        double[] templateData = this.getData();
        //声明一个double值用于保存最后结果并返回。
        double result = 0;
        //声明int类型保存波峰的位置
        int peakInData, peakInTemplate;
        peakInData = Peak.findPeak(apWave);
        peakInTemplate = Peak.findPeak(this);

        if (peakInData == -1) {
            result = 0;
        }
        if (peakInData < peakInTemplate) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(data[0], peakInTemplate - peakInData);
            data = MathMethods.arraysMerge(addArrays, data);
        } else if (peakInData > peakInTemplate) {
            double[] addArrays = MathMethods.creatArrays(templateData[0], peakInData - peakInTemplate);
            templateData = MathMethods.arraysMerge(addArrays, templateData);
        }
        if (data.length < templateData.length) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(data[data.length - 1], templateData.length - data.length);
            data = MathMethods.arraysMerge(data, addArrays);
        } else if (data.length > templateData.length) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(templateData[templateData.length - 1], data.length - templateData.length);
            templateData = MathMethods.arraysMerge(templateData, addArrays);
        }
        if (data.length != templateData.length) {
            return result = 0;
        }
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        result = pearsonsCorrelation.correlation(data, templateData);
        return result;
    }

    public Wave mergeWave(Wave apWave) {
        double[] data = apWave.getData();
        double[] templateData = this.getData();
        double[] result;
        int peakInData, peakInTemplate;
        peakInData = Peak.findPeak(apWave);
        peakInTemplate = Peak.findPeak(this);
        data = MathMethods.arraysMinus(data, MathMethods.minInArrays(data));
        if (peakInData < peakInTemplate) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(data[0], peakInTemplate - peakInData);
            data = MathMethods.arraysMerge(addArrays, data);
        } else if (peakInData > peakInTemplate) {
            double[] addArrays = MathMethods.creatArrays(templateData[0], peakInData - peakInTemplate);
            templateData = MathMethods.arraysMerge(addArrays, templateData);
        }
        if (data.length < templateData.length) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(data[data.length - 1], templateData.length - data.length);
            data = MathMethods.arraysMerge(data, addArrays);
        } else if (data.length > templateData.length) {
            double[] addArrays;
            addArrays = MathMethods.creatArrays(templateData[templateData.length - 1], data.length - templateData.length);
            templateData = MathMethods.arraysMerge(templateData, addArrays);
        }
        if (data.length == templateData.length) {
            result = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                result[i] = (data[i] + templateData[i]) / 2;
            }
            return new APWave(result, apWave.rate);
        } else {
            result = new double[0];
            return new APWave(result, apWave.rate);
        }
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }
}
