package com.songc.dao;

import com.songc.entity.HbaseFile;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public class HbaseFileDao {

    @Autowired
    private HbaseTemplate hbaseTemplate;
    private String tableName = "hfile";
    public static byte[] HF_INFO = Bytes.toBytes("hfInfo");

    private byte[] qParentId = Bytes.toBytes("parentId");
    private byte[] qName = Bytes.toBytes("name");
    private byte[] qContent = Bytes.toBytes("content");

    public List<HbaseFile> findAll(){
        return hbaseTemplate.find(tableName, "hfInfo", new RowMapper<HbaseFile>() {
            @Override
            public HbaseFile mapRow(Result result, int i) throws Exception {
                return new HbaseFile(Bytes.toLong(result.getValue(HF_INFO,qParentId)),Bytes.toString(result.getValue(HF_INFO,qName)),
                        result.getValue(HF_INFO,qContent));
            }
        });
    }

    public HbaseFile save(final Long parentId,final String name, final byte[] content) {
        return hbaseTemplate.execute(tableName, new TableCallback<HbaseFile>() {
            @Override
            public HbaseFile doInTable(HTableInterface hTableInterface) throws Throwable {
                HbaseFile hbaseFile = new HbaseFile(parentId,name, content);
                String rowKey = String.format("%016d", parentId) + String.format("%016d", HbaseFile.HF_ID);
                Put p = new Put(Bytes.toBytes(rowKey));
                p.add(HF_INFO, qParentId, Bytes.toBytes(parentId));
                p.add(HF_INFO, qName, Bytes.toBytes(name));
                p.add(HF_INFO, qContent, content);
                hTableInterface.put(p);
                return hbaseFile;
            }
        });
    }

}
