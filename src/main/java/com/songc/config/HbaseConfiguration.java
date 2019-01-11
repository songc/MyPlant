package com.songc.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class HbaseConfiguration {

    @Value("${hbase.rootdir}")
    private String rootDir;
    @Value("${hbase.quorum}")
    private String quorum;
    @Value("${hbase.clientPort}")
    private String clientPort;
    @Value("${hbase.maxSize}")
    private String maxSize;

    /**
     * 启动前必须打开HBase，不然该方法会报错，造成启动失败
     *
     * @return HBase 的connection对象，用于连接HBase 进行HBase的相关操作
     * @throws IOException
     */
    @Bean
    public Connection initialize() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.roodir", rootDir);
        configuration.set("hbase.zookeeper.quorum", quorum);
        configuration.set("hbase.zookeeper.property.clientPort", clientPort);
        configuration.set("hbase.client.keyvalue.maxsize", maxSize);
        return ConnectionFactory.createConnection(configuration);
    }
}
