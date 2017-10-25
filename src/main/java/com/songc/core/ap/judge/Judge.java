package com.songc.core.ap.judge;

import com.songc.core.ap.detect.wave.TemplateWave;
import com.songc.core.ap.detect.wave.Wave;
import com.songc.core.ap.util.MathMethods;
import com.songc.core.ap.util.StringUtil;
import com.songc.core.ap.util.fileoperation.ExcelReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songc on 2/16/2017.
 */
public class Judge {
    //todo 这里需要进行改动 文件的路径问题
    private final static String TEMPLATE_PATH = "E:\\File\\AP\\temp_update_2017-2-14.xlsx";
    private final static double THRESHOLD = 0.9;
    private final static double MERGE_THRESHOLD = 0.95;
    private final static int RATE = 10;
    private static Templates templates;

    private Judge(Templates templates) {
        Judge.templates = templates;
    }

    private Judge() {
        List<Wave> templates = new ArrayList<Wave>();
        ExcelReader excelReader = new ExcelReader();
        //读取模板excel中的数据并返回ArrayList类型数据。
        ArrayList<ArrayList<String>> template = null;
        try {
            template = excelReader.readExcelByColumns(TEMPLATE_PATH, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ArrayList<String> tmp :
                template) {
            String[] templateString = (String[]) tmp.toArray(new String[0]);
            double[] templateDouble = StringUtil.getDouble(templateString);
            templates.add(new TemplateWave(templateDouble, RATE));
        }
        Judge judge = new Judge(new Templates(templates));
    }

    public static final Judge getInstance() {
        return JudgeHolder.INSTANCE;
    }

    public Boolean isAP(Wave apWave) {
        double[] corrValues = templates.corrValues(apWave);
        double maxCorr = MathMethods.maxInArrays(corrValues);
        int index = MathMethods.search(corrValues, maxCorr);
        if (maxCorr > MERGE_THRESHOLD) {
            templates.mergeTemplate(apWave, index);
            return true;
        } else if (maxCorr > THRESHOLD) {
            templates.addTempalte(apWave);
            return true;
        } else {
            return false;
        }
    }

    public static class JudgeHolder {
        private static final Judge INSTANCE = new Judge();
    }
}
