package com.songc.dao.imp;

import com.songc.dao.Hbase;
import com.songc.dto.HbaseFileDTO;
import com.songc.dto.HbaseFileWithContentDTO;
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

    @Value("${the.hbase.table-name}")
    private String tableName;

    @Value("${the.hbase.family-name.meta}")
    private String metaFamily;

    @Value("${the.hbase.family-name.content}")
    private String contentFamily;

    private byte[] qParentId = Bytes.toBytes("parentId");
    private byte[] qName = Bytes.toBytes("name");
    private byte[] qContent = Bytes.toBytes("content");
    private byte[] qSampleId = Bytes.toBytes("sampleId");
    private byte[] qImageMetaId = Bytes.toBytes("imageMetaId");
    private byte[] qIecMetaId = Bytes.toBytes("iecMetaId");
    private byte[] qEnvironmentId = Bytes.toBytes("environmentId");
    private byte[] qSoftwareId = Bytes.toBytes("softwareId");

    private Connection connection;

    @Autowired
    public HbaseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public HbaseFile save(HbaseFile hbaseFile) {
        String rowKey = HbaseUtil.convertRowKey(hbaseFile.getParentId());
        hbaseFile.setRowKey(rowKey);
        Put p = getPut(hbaseFile, rowKey);
        Put p2 = getContentPut(hbaseFile, rowKey);
        doPut(p);
        doPut(p2);
        return hbaseFile;
    }

    @Override
    public List<HbaseFile> save(List<HbaseFile> hbaseFiles) {
        List<Put> puts = hbaseFiles.stream().peek(hbaseFile -> {
            String rowKey = HbaseUtil.convertRowKey(hbaseFile.getParentId());
            hbaseFile.setRowKey(rowKey);
        }).map(hbaseFile -> getPut(hbaseFile, hbaseFile.getRowKey())).collect(Collectors.toList());
        List<Put> puts2 = hbaseFiles.stream().map(hbaseFile -> getContentPut(hbaseFile, hbaseFile.getRowKey()))
                .collect(Collectors.toList());
        doPut(puts);
        doPut(puts2);
        return hbaseFiles;
    }

    @Override
    public HbaseFileWithContentDTO find(String rowKey) {
        Get g = new Get(rowKey.getBytes());
        g.addFamily(contentFamily.getBytes());
        Result result = doGet(g);
        return getHbaseFileContent(result);
    }

    @Override
    public List<HbaseFileDTO> findAll() {
        Scan scan = new Scan();
        ResultScanner results = getScanner(scan);
        List<HbaseFileDTO> hbaseFiles = new ArrayList<>();
        for (Result result : results) {
            HbaseFileDTO hbaseFile = getHbaseFile(result);
            hbaseFiles.add(hbaseFile);
        }
        results.close();
        return hbaseFiles;
    }

    @Override
    public List<HbaseFileDTO> findByParentId(Long parentId) {
        String prefix = StringUtils.reverse(String.format("%016d", parentId));
        Scan scan = new Scan(prefix.getBytes());
        scan.setFilter(new PrefixFilter(prefix.getBytes()));
        scan.addFamily(metaFamily.getBytes());
        ResultScanner results = getScanner(scan);
        List<HbaseFileDTO> hbaseFiles = new ArrayList<>();
        for (Result result : results) {
            HbaseFileDTO hbaseFile = getHbaseFile(result);
            hbaseFiles.add(hbaseFile);
        }
        results.close();
        return hbaseFiles;
    }

    @Override
    public List<HbaseFileWithContentDTO> findContentByParentId(Long parentId) {
        String prefix = StringUtils.reverse(String.format("%016d", parentId));
        Scan scan = new Scan(prefix.getBytes());
        scan.setFilter(new PrefixFilter(prefix.getBytes()));
        scan.addFamily(contentFamily.getBytes());
        ResultScanner results = getScanner(scan);
        List<HbaseFileWithContentDTO> hbaseFiles = new ArrayList<>();
        for (Result result : results) {
            HbaseFileWithContentDTO hbaseFile = getHbaseFileContent(result);
            hbaseFiles.add(hbaseFile);
        }
        results.close();
        return hbaseFiles;
    }

    @Override
    public void delete(String rowKey) {
        Delete delete = new Delete(rowKey.getBytes());
        doDelete(delete);
    }

    @Override
    public void deleteByParentId(Long parentId) {
        doDelete(this.getRowKeysByParentId(parentId)
                .stream()
                .map(Delete::new)
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(List<String> rowKeyList) {
        doDelete(rowKeyList.stream()
                .map(String::getBytes)
                .map(Delete::new)
                .collect(Collectors.toList()));
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
        for (Result result : results) {
            rowKeys.add(result.getRow());
        }
        results.close();
        return rowKeys;
    }

    private void doPut(List<Put> p) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Result doGet(Get g) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            return table.get(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void doPut(Put p) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Put getPut(HbaseFile hbaseFile, String rowKey) {
        Put p = new Put(Bytes.toBytes(rowKey));
        p.addColumn(metaFamily.getBytes(), qParentId, Bytes.toBytes(hbaseFile.getParentId()));
        p.addColumn(metaFamily.getBytes(), qName, hbaseFile.getName().getBytes());
        if (hbaseFile.getIecMetaId() != null) {
            p.addColumn(metaFamily.getBytes(), qIecMetaId, Bytes.toBytes(hbaseFile.getIecMetaId()));
        }
        if (hbaseFile.getSampleId() != null) {
            p.addColumn(metaFamily.getBytes(), qSampleId, Bytes.toBytes(hbaseFile.getSampleId()));
        }
        if (hbaseFile.getImageMetaId() != null) {
            p.addColumn(metaFamily.getBytes(), qImageMetaId, Bytes.toBytes(hbaseFile.getImageMetaId()));
        }
        if (hbaseFile.getEnvironmentId() != null) {
            p.addColumn(metaFamily.getBytes(), qEnvironmentId, Bytes.toBytes(hbaseFile.getEnvironmentId()));
        }
        if (hbaseFile.getSoftwareId() != null) {
            p.addColumn(metaFamily.getBytes(), qSoftwareId, Bytes.toBytes(hbaseFile.getSoftwareId()));
        }
        return p;
    }

    private Put getContentPut(HbaseFile hbaseFile, String rowKey) {
        Put p = new Put(Bytes.toBytes(rowKey));
        p.addColumn(contentFamily.getBytes(), qName, hbaseFile.getName().getBytes());
        p.addColumn(contentFamily.getBytes(), qContent, hbaseFile.getContent());
        return p;
    }

    private ResultScanner getScanner(Scan scan) {
        ResultScanner results = null;
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            results = table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    private HbaseFileDTO getHbaseFile(Result result) {
        HbaseFileDTO hbaseFile = new HbaseFileDTO();
        hbaseFile.setRowKey(new String(result.getRow()));
        hbaseFile.setParentId(Bytes.toLong(result.getValue(metaFamily.getBytes(), qParentId)));
        hbaseFile.setName(Bytes.toString(result.getValue(metaFamily.getBytes(), qName)));
        if (result.getValue(metaFamily.getBytes(), qEnvironmentId) != null) {
            hbaseFile.setEnvironmentId(Bytes.toLong(result.getValue(metaFamily.getBytes(), qEnvironmentId)));
        }
        if (result.getValue(metaFamily.getBytes(), qImageMetaId) != null) {
            hbaseFile.setImageMetaId(Bytes.toLong(result.getValue(metaFamily.getBytes(), qImageMetaId)));
        }
        if (result.getValue(metaFamily.getBytes(), qSampleId) != null) {
            hbaseFile.setSampleId(Bytes.toLong(result.getValue(metaFamily.getBytes(), qSampleId)));
        }
        if (result.getValue(metaFamily.getBytes(), qIecMetaId) != null) {
            hbaseFile.setIecMetaId(Bytes.toLong(result.getValue(metaFamily.getBytes(), qIecMetaId)));
        }
        if (result.getValue(metaFamily.getBytes(), qSoftwareId) != null) {
            hbaseFile.setSoftwareId(Bytes.toLong(result.getValue(metaFamily.getBytes(), qSoftwareId)));
        }
        return hbaseFile;
    }

    private HbaseFileWithContentDTO getHbaseFileContent(Result result) {
        HbaseFileWithContentDTO hbaseFile = new HbaseFileWithContentDTO();
        hbaseFile.setRowKey(new String(result.getRow()));
        hbaseFile.setName(new String(result.getValue(contentFamily.getBytes(), qName)));
        hbaseFile.setContent(result.getValue(contentFamily.getBytes(), qContent));
        return hbaseFile;
    }

    private void doDelete(Delete delete) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doDelete(List<Delete> deleteList) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.delete(deleteList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
