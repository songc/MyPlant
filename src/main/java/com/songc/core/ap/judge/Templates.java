package com.songc.core.ap.judge;

import com.songc.core.ap.detect.wave.Wave;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songc on 2/16/2017.
 */
public class Templates {
    List<Wave> Templates;

    public Templates(List<Wave> templates) {
        this.Templates = templates;
    }

    double[] corrValues(Wave apWave) {
        List<Double> result = new ArrayList<Double>();
        for (Wave template :
                Templates) {
            result.add(template.corrValue(apWave));
        }
        int size = result.size();
        double[] values = new double[size];
        for (int i = 0; i < size; i++) {
            values[i] = result.get(i);
        }
        return values;
    }

    void addTempalte(Wave templateWave) {
        Templates.add(templateWave);
    }

    void mergeTemplate(Wave apWave, int index) {
        Templates.get(index).mergeWave(apWave);
    }
}
