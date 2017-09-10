package com.songc.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@org.springframework.context.annotation.Configuration
public class HbaseConfiguration {

    private Configuration configuration;


    @Bean
    public HbaseTemplate initialize(){
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.roodir","hdfs://master:54310/hbase");
        configuration.set("hbase.zookeeper.quorum","master");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        return new HbaseTemplate(configuration);
    }
}
