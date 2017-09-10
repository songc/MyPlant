package com.songc.dao.imp;

import com.songc.dao.Hbase;
import com.songc.entity.HbaseFile;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HbaseDao implements Hbase {

    @Value("${hbase.table.name}")
    private String tableName;

    private String family="h_info";

    private byte[] qParentId = Bytes.toBytes("parentId");
    private byte[] qName = Bytes.toBytes("name");
    private byte[] qContent = Bytes.toBytes("content");
    private byte[] familyAsBytes = Bytes.toBytes("h_info");

    @Autowired
    private HbaseTemplate hbaseTemplate;

    @Override
    public HbaseFile find(String rowName) {
        return hbaseTemplate.get(tableName, rowName, new RowMapper<HbaseFile>() {
            @Override
            public HbaseFile mapRow(Result result, int i) throws Exception {
                return new HbaseFile(Bytes.toLong(result.getValue(familyAsBytes,qParentId))
                        ,Bytes.toString(result.getValue(familyAsBytes,qName)),result.getValue(familyAsBytes,qContent));
            }
        });
    }

    @Override
    public HbaseFile save(Long parentId, String name, byte[] content) {
        return hbaseTemplate.execute(tableName, new TableCallback<HbaseFile>() {
            @Override
            public HbaseFile doInTable(HTableInterface hTableInterface) throws Throwable {
                String rowKey = String.format("%016d", parentId) + String.format("%016d", HbaseFile.HF_ID);
                Put p = new Put(Bytes.toBytes(rowKey));
                p.add(familyAsBytes, qParentId, Bytes.toBytes(parentId));
                p.add(familyAsBytes, qName, Bytes.toBytes(name));
                p.add(familyAsBytes, qContent, content);
                hTableInterface.put(p);
                return new HbaseFile(parentId,name,content);
            }
        });
    }

    @Override
    public List<HbaseFile> findAll() {
        return hbaseTemplate.find(tableName,family, new RowMapper<HbaseFile>() {
            @Override
            public HbaseFile mapRow(Result result, int i) throws Exception {
                return new HbaseFile(Bytes.toLong(result.getValue(familyAsBytes,qParentId))
                        ,Bytes.toString(result.getValue(familyAsBytes,qName)),result.getValue(familyAsBytes,qContent));
            }
        });
    }

    @Override
    public void delete(String rowName) {
        hbaseTemplate.delete(tableName,rowName,family);
    }

    @Override
    public String update(String rowKey, HbaseFile hbaseFile) {
        return null;
    }
}
