package com.songc.core.ap.util.fileoperation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by songc on 2/15/2017.
 */
public class CSVFileReader extends FileToRead {
    String path;

    public CSVFileReader(String path) {
        this.path = path;
    }

    public String read() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(this.path));
        String tmpString = null;
        StringBuilder stringBuilder = new StringBuilder();
        while ((tmpString = in.readLine()) != null) {
            stringBuilder.append(tmpString);
            stringBuilder.append("\n");
        }
        in.close();
        return stringBuilder.toString();
    }
}
