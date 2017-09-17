package com.songc.dao.imp;

import com.songc.dao.Hbase;
import com.songc.entity.HbaseFile;
import com.songc.util.HbaseUtil;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    private HbaseTemplate hbaseTemplate;

    @Autowired
    public HbaseDao(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    @Override
    public HbaseFile save(HbaseFile hbaseFile) {
        return hbaseTemplate.execute(tableName, new TableCallback<HbaseFile>() {
            @Override
            public HbaseFile doInTable(HTableInterface hTableInterface) throws Throwable {
                String rowKey = HbaseUtil.ConvertRowKey(hbaseFile.getParentId());
                Put p = new Put(Bytes.toBytes(rowKey));
                p.add(familyAsBytes, qParentId, Bytes.toBytes(hbaseFile.getParentId()));
                p.add(familyAsBytes, qName, Bytes.toBytes(hbaseFile.getName()));
                p.add(familyAsBytes, qContent, hbaseFile.getContent());
                hTableInterface.put(p);
                return hbaseFile;
            }
        });
    }

    @Override
    public List<HbaseFile> save(List<HbaseFile> hbaseFiles) {
        hbaseTemplate.execute(tableName, new TableCallback<HbaseFile>() {
            @Override
            public HbaseFile doInTable(HTableInterface hTableInterface) throws Throwable {
                List<Put> puts = new ArrayList<>();
                for (HbaseFile hbaseFile : hbaseFiles) {
                    String rowKey = HbaseUtil.ConvertRowKey(hbaseFile.getParentId());
                    Put p = new Put(Bytes.toBytes(rowKey));
                    p.add(familyAsBytes, qParentId, Bytes.toBytes(hbaseFile.getParentId()));
                    p.add(familyAsBytes, qName, Bytes.toBytes(hbaseFile.getName()));
                    p.add(familyAsBytes, qContent, hbaseFile.getContent());
                    puts.add(p);
                }
                hTableInterface.put(puts);
                return null;
            }
        });
        return hbaseFiles;
    }

    @Override
    public HbaseFile find(String rowName) {
        return hbaseTemplate.get(tableName, rowName, new RowMapper<HbaseFile>() {
            @Override
            public HbaseFile mapRow(Result result, int i) throws Exception {
                return new HbaseFile(Bytes.toLong(result.getValue(familyAsBytes, qParentId))
                        , Bytes.toString(result.getValue(familyAsBytes, qName)), result.getValue(familyAsBytes, qContent));
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
    public List<HbaseFile> findByParentId(Long parentId) {
        return null;
    }

    @Override
    public void delete(String rowName) {
        hbaseTemplate.delete(tableName, rowName, family);
    }

    @Override
    public void deleteByParentId(Long parentId) {

    }

    @Override
    public String update(String rowKey, HbaseFile hbaseFile) {
        return null;
    }
}
