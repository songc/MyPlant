package com.songc.dao.imp;

import com.songc.dao.Hbase;
import com.songc.entity.HbaseFile;
import com.songc.util.HbaseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HbaseDao implements Hbase {

    @Value("${hbase.table.name}")
    private String tableName;

    private static String family = "h_info";

    private byte[] qParentId = Bytes.toBytes("parentId");
    private byte[] qName = Bytes.toBytes("name");
    private byte[] qContent = Bytes.toBytes("content");
    private static byte[] qFamily;

    static {
        qFamily = family.getBytes();
    }

    private Connection connection;

    @Autowired
    public HbaseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public HbaseFile save(HbaseFile hbaseFile) {

        String rowKey = HbaseUtil.convertRowKey(hbaseFile.getParentId());
        hbaseFile.setRowKey(rowKey);
        Put p = new Put(rowKey.getBytes());
        p.addColumn(family.getBytes(), qParentId, Bytes.toBytes(hbaseFile.getParentId()));
        p.addColumn(family.getBytes(), qName, hbaseFile.getName().getBytes());
        p.addColumn(family.getBytes(), qContent, hbaseFile.getContent());
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(p);
            return hbaseFile;
        } catch (IOException e) {
            e.printStackTrace();
            hbaseFile.setRowKey(null);
        }
        return hbaseFile;
    }

    @Override
    public List<HbaseFile> save(List<HbaseFile> hbaseFiles) {
        List<Put> puts = new ArrayList<>();
        for (HbaseFile hbaseFile : hbaseFiles) {
            String rowKey = HbaseUtil.convertRowKey(hbaseFile.getParentId());
            hbaseFile.setRowKey(rowKey);
            Put p = new Put(Bytes.toBytes(rowKey));
            p.addColumn(qFamily, qParentId, Bytes.toBytes(hbaseFile.getParentId()));
            p.addColumn(qFamily, qName, hbaseFile.getName().getBytes());
            p.addColumn(qFamily, qContent, hbaseFile.getContent());
            puts.add(p);
        }
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(puts);
            return hbaseFiles;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hbaseFiles;
    }

    @Override
    public HbaseFile find(String rowKey) {
        Get g = new Get(rowKey.getBytes());
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Result result = table.get(g);
            return new HbaseFile(new String(result.getRow()), Bytes.toLong(result.getValue(qFamily, qParentId)),
                    Bytes.toString(result.getValue(qFamily, qName)),
                    result.getValue(qFamily, qContent));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HbaseFile> findAll() {
        Scan scan = new Scan();
        ResultScanner results;
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        results = getScanner(scan);
        if (results != null) {
            for (Result result : results) {
                hbaseFiles.add(new HbaseFile(new String(result.getRow()), Bytes.toLong(result.getValue(qFamily, qParentId))
                        , Bytes.toString(result.getValue(qFamily, qName)), result.getValue(qFamily, qContent)));
            }
        }
        return hbaseFiles;
    }

    private ResultScanner getScanner(Scan scan) {
        ResultScanner results;
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            results = table.getScanner(scan);
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HbaseFile> findByParentId(Long parentId) {
        String prefix = StringUtils.reverse(String.format("%016d", parentId));
        Scan scan = new Scan(prefix.getBytes());
        scan.setFilter(new PrefixFilter(prefix.getBytes()));
        ResultScanner results = getScanner(scan);
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        if (results != null) {
            for (Result result : results) {
                hbaseFiles.add(new HbaseFile(new String(result.getRow()), Bytes.toLong(result.getValue(qFamily, qParentId))
                        , Bytes.toString(result.getValue(qFamily, qName)), result.getValue(qFamily, qContent)));
            }
        }
        return hbaseFiles;
    }

    @Override
    public void delete(String rowKey) {
        Delete delete = new Delete(rowKey.getBytes());
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByParentId(Long parentId) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.delete(this.getRowKeysByParentId(parentId)
                    .stream()
                    .map(Delete::new)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(List<String> rowKeyList) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.delete(rowKeyList.stream()
                    .map(String::getBytes)
                    .map(Delete::new)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<byte[]> getRowKeysByParentId(Long parentId) {
        String prefix = StringUtils.reverse(String.format("%016d", parentId));
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList.addFilter(new KeyOnlyFilter(true));
        filterList.addFilter(new PrefixFilter(prefix.getBytes()));
        Scan scan = new Scan(prefix.getBytes());
        scan.setFilter(filterList);
        List<byte[]> rowKeys = new ArrayList<>();
        ResultScanner results = getScanner(scan);
        if (results != null) {
            for (Result result : results) {
                rowKeys.add(result.getRow());
            }
        }
        return rowKeys;
    }
}
