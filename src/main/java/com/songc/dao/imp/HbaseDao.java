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
    private byte[] qSampleId = Bytes.toBytes("sampleId");
    private byte[] qImageMetaId = Bytes.toBytes("imageMetaId");
    private byte[] qIecMetaId = Bytes.toBytes("iecMetaId");
    private byte[] qEnvironmentId = Bytes.toBytes("environmentId");
    private byte[] qSoftwareId = Bytes.toBytes("softwareId");
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
        Put p = getPut(hbaseFile, rowKey);
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(p);
            hbaseFile.setRowKey(rowKey);
            return hbaseFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hbaseFile;
    }

    @Override
    public List<HbaseFile> save(List<HbaseFile> hbaseFiles) {
        List<Put> puts = new ArrayList<>();
        for (HbaseFile hbaseFile : hbaseFiles) {
            String rowKey = HbaseUtil.convertRowKey(hbaseFile.getParentId());
            hbaseFile.setRowKey(rowKey);
            Put p = getPut(hbaseFile, rowKey);
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

    private Put getPut(HbaseFile hbaseFile, String rowKey) {
        Put p = new Put(Bytes.toBytes(rowKey));
        p.addColumn(qFamily, qParentId, Bytes.toBytes(hbaseFile.getParentId()));
        p.addColumn(qFamily, qName, hbaseFile.getName().getBytes());
        p.addColumn(qFamily, qContent, hbaseFile.getContent());
        if (hbaseFile.getIecMetaId() != null) {
            p.addColumn(qFamily, qIecMetaId, Bytes.toBytes(hbaseFile.getIecMetaId()));
        }
        if (hbaseFile.getSampleId() != null) {
            p.addColumn(qFamily, qSampleId, Bytes.toBytes(hbaseFile.getSampleId()));
        }
        if (hbaseFile.getImageMetaId() != null) {
            p.addColumn(qFamily, qImageMetaId, Bytes.toBytes(hbaseFile.getImageMetaId()));
        }
        if (hbaseFile.getEnvironmentId() != null) {
            p.addColumn(qFamily, qEnvironmentId, Bytes.toBytes(hbaseFile.getEnvironmentId()));
        }
        if (hbaseFile.getSoftwareId() != null) {
            p.addColumn(qFamily, qSoftwareId, Bytes.toBytes(hbaseFile.getSoftwareId()));
        }
        return p;
    }

    @Override
    public HbaseFile find(String rowKey) {
        Get g = new Get(rowKey.getBytes());
        HbaseFile hbaseFile = null;
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Result result = table.get(g);
            hbaseFile = getHbaseFile(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hbaseFile;
    }

    @Override
    public List<HbaseFile> findAll() {
        Scan scan = new Scan();
        ResultScanner results;
        List<HbaseFile> hbaseFiles = new ArrayList<>();
        results = getScanner(scan);
        if (results != null) {
            for (Result result : results) {
                HbaseFile hbaseFile = getHbaseFile(result);
                hbaseFiles.add(hbaseFile);
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
                HbaseFile hbaseFile = getHbaseFile(result);
                hbaseFiles.add(hbaseFile);
            }
        }
        return hbaseFiles;
    }

    private HbaseFile getHbaseFile(Result result) {
        HbaseFile hbaseFile = new HbaseFile(new String(result.getRow()), Bytes.toLong(result.getValue(qFamily, qParentId))
                , Bytes.toString(result.getValue(qFamily, qName)), result.getValue(qFamily, qContent));
        if (result.getValue(qFamily, qEnvironmentId) != null) {
            hbaseFile.setEnvironmentId(Bytes.toLong(result.getValue(qFamily, qEnvironmentId)));
        }
        if (result.getValue(qFamily, qImageMetaId) != null) {
            hbaseFile.setImageMetaId(Bytes.toLong(result.getValue(qFamily, qImageMetaId)));
        }
        if (result.getValue(qFamily, qSampleId) != null) {
            hbaseFile.setSampleId(Bytes.toLong(result.getValue(qFamily, qSampleId)));
        }
        if (result.getValue(qFamily, qIecMetaId) != null) {
            hbaseFile.setIecMetaId(Bytes.toLong(result.getValue(qFamily, qIecMetaId)));
        }
        if (result.getValue(qFamily, qSoftwareId) != null) {
            hbaseFile.setSoftwareId(Bytes.toLong(result.getValue(qFamily, qSoftwareId)));
        }
        return hbaseFile;
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
        assert results != null;
        for (Result result : results) {
            rowKeys.add(result.getRow());
        }
        results.close();
        return rowKeys;
    }
}
