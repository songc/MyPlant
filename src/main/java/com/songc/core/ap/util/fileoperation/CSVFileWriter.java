package com.songc.core.ap.util.fileoperation;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by songc on 2/15/2017.
 */
public class CSVFileWriter extends FIleToWrite {
    String path;

    public CSVFileWriter(String path) {
        this.path = path;
    }

    void writer(String content) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.path), "GBK"));
        ;
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }
}
