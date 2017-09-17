package com.songc.dao.imp;

import com.songc.dao.Hbase;
import com.songc.entity.HbaseFile;
import com.songc.util.HbaseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HbaseDao implements Hbase {

    @Value("${hbase.table.name}")
    private String tableName;

    private String family="h_info";

    private byte[] qParentId = Bytes.toBytes("parentId");
    private byte[] qName = Bytes.toBytes("name");
    private byte[] qContent = Bytes.toBytes("content");

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
                Put p = new Put(rowKey.getBytes());
                p.add(family.getBytes(), qParentId, Bytes.toBytes(hbaseFile.getParentId()));
                p.add(family.getBytes(), qName, hbaseFile.getName().getBytes());
                p.add(family.getBytes(), qContent, hbaseFile.getContent());
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
                    p.add(family.getBytes(), qParentId, Bytes.toBytes(hbaseFile.getParentId()));
                    p.add(family.getBytes(), qName, hbaseFile.getName().getBytes());
                    p.add(family.getBytes(), qContent, hbaseFile.getContent());
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
                return new HbaseFile(Bytes.toLong(result.getValue(family.getBytes(), qParentId)),
                        Bytes.toString(result.getValue(family.getBytes(), qName)),
                        result.getValue(family.getBytes(), qContent));
            }
        });
    }

    @Override
    public List<HbaseFile> findAll() {
        return hbaseTemplate.find(tableName,family, new RowMapper<HbaseFile>() {
            @Override
            public HbaseFile mapRow(Result result, int i) throws Exception {
                return new HbaseFile(Bytes.toLong(result.getValue(family.getBytes(), qParentId))
                        , Bytes.toString(result.getValue(family.getBytes(), qName)), result.getValue(family.getBytes(), qContent));
            }
        });
    }

    @Override
    public List<HbaseFile> findByParentId(Long parentId) {
        String prefix = StringUtils.reverse(String.format("%016d", parentId));
        Scan scan = new Scan(prefix.getBytes());
        scan.setFilter(new PrefixFilter(prefix.getBytes()));
        return hbaseTemplate.find(tableName, scan, new RowMapper<HbaseFile>() {
            @Override
            public HbaseFile mapRow(Result result, int i) throws Exception {
                return new HbaseFile(Bytes.toLong(result.getValue(family.getBytes(), qParentId))
                        , Bytes.toString(result.getValue(family.getBytes(), qName))
                        , result.getValue(family.getBytes(), qContent));
            }
        });
    }

    @Override
    public void delete(String rowName) {
        hbaseTemplate.delete(tableName, rowName, family);
    }

    @Override
    public void deleteByParentId(Long parentId) {
        hbaseTemplate.execute(tableName, new TableCallback<HbaseFile>() {
            @Override
            public HbaseFile doInTable(HTableInterface hTableInterface) throws Throwable {
                List<Delete> deletes = new ArrayList<>();
                for (byte[] rowKey : getRowKeysByParentId(parentId)) {
                    deletes.add(new Delete(rowKey));
                }
                hTableInterface.delete(deletes);
                return null;
            }
        });
    }

    @Override
    public List<byte[]> getRowKeysByParentId(Long parentId) {
        String prefix = StringUtils.reverse(String.format("%016d", parentId));
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(new KeyOnlyFilter(true));
        filterList.addFilter(new PrefixFilter(prefix.getBytes()));
        Scan scan = new Scan(prefix.getBytes());
        scan.setFilter(filterList);
        List<byte[]> rowkeys = new ArrayList<>();
        hbaseTemplate.execute(tableName, new TableCallback<Object>() {
            @Override
            public Object doInTable(HTableInterface hTableInterface) throws Throwable {
                ResultScanner results = hTableInterface.getScanner(scan);
                for (Result result : results) {
                    rowkeys.add(result.getRow());
                }
                return null;
            }
        });
        return rowkeys;
    }
}
