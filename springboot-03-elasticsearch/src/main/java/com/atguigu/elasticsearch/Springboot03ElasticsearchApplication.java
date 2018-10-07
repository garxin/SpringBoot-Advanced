package com.atguigu.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot默认支持两种技术来和ES交互
 * 1.Jest，需要导入Jest工具包（io.searchbox.client.JestClient），默认不生效
 * 2.SpringDataElasticSearch
 * 1).Client：clusterNodes节点信息，clusterName节点名称
 * 2).ElasticSearchTemplate操作ES
 * 3).编写一个ElasticSearchRepository的子接口来操作ES
 */
@SpringBootApplication
public class Springboot03ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot03ElasticsearchApplication.class, args);
    }

}
