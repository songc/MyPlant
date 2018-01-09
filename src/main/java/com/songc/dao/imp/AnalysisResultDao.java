package com.songc.dao.imp;

import com.songc.dao.AnalysisResult;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created By @author songc
 * on 2018/1/9
 */
@Component
public class AnalysisResultDao implements AnalysisResult {

    private String tableName = "analysis_result";
    private String familyName = "image";
    private byte[] qContent = Bytes.toBytes("result");

    private Connection connection;

    @Autowired
    public AnalysisResultDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String save(String rowKey, String json) {
        Put put = new Put(rowKey.getBytes());
        put.addColumn(familyName.getBytes(), qContent, json.getBytes());
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String find(String rowKey) {
        Get get = new Get(rowKey.getBytes());
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            if (table.exists(get)) {
                Result result = table.get(get);
                return new String(result.getValue(familyName.getBytes(), qContent));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
