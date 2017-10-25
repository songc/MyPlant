package com.songc.core.ap.util.fileoperation;

import java.io.IOException;

/**
 * Created by songc on 2/15/2017.
 */
public abstract class FIleToWrite {
    String path;

    abstract void writer(String content) throws IOException;
}
