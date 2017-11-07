package com.songc.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class HbaseConfiguration {

    private Configuration configuration;


    @Bean
    public Connection initialize() throws IOException {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.roodir", "hdfs://hbase-spark:9000/hbase");
        configuration.set("hbase.zookeeper.quorum", "hbase-spark,hbase-spark1,hbase-spark2");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.client.keyvalue.maxsize", "524288000");
        return ConnectionFactory.createConnection(configuration);
    }
}
