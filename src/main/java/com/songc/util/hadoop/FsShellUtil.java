package com.songc.util.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class FsShellUtil {
    private FileSystem fileSystem;
    public FsShellUtil() throws IOException {
        String PATH = "hdfs://10.2.205.228:54310";
        Configuration configuration = new Configuration();
        this.fileSystem = FileSystem.get(URI.create(PATH), configuration);
    }

    public void writer(String fileName, byte[] content) throws IOException {
        Path path = new Path("/usr/songc/"+fileName);
        if (fileSystem.exists(path)) {
            System.out.println("File already exists");
        }
        FSDataOutputStream out = fileSystem.create(path);
        out.write(content);
        out.close();
        System.out.println(fileName + " save success");
    }

}


