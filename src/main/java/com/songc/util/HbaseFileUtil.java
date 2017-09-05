package com.songc.util;

import com.songc.dao.HbaseFileDao;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HbaseFileUtil{
    private String tableName = "hfile";
    private byte[] tableNameAsBytes = Bytes.toBytes(tableName);

    private Configuration configuration;

    private HBaseAdmin admin;

    public void initialize() throws IOException {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","master");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        admin = new HBaseAdmin(configuration);
        if(admin.tableExists(tableNameAsBytes)){
            if (!admin.isTableDisabled(tableNameAsBytes)){
                System.out.printf("Disabling %s\n", tableName);
                admin.disableTable(tableNameAsBytes);
            }
            System.out.printf("Deleting %s\n", tableName);
            admin.deleteTable(tableNameAsBytes);
        }
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(HbaseFileDao.HF_INFO);
        tableDescriptor.addFamily(columnDescriptor);
        admin.createTable(tableDescriptor);
    }

}
