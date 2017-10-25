package com.songc.core.ap.util.fileoperation;

import java.io.File;

/**
 * Created by songc on 2/15/2017.
 */
public class Folder {
    private String folderName;

    public Folder(String folderName) {
        this.folderName = folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    /**
     * 获取该目录下的所有文件的绝对路径的字符串
     * 方便我们一个个读取该文件。
     */
    public String[] getFileListString() {
        File file = new File(this.folderName);
        File[] files = file.listFiles();
        String[] listString = new String[files.length];
        int i = 0;
        for (File tmpfile :
                files) {
            listString[i++] = tmpfile.getAbsolutePath().toString();
        }
        return listString;
    }

}

