package com.atguigu.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 快速体验缓存：
 * 1.开启基于注解的缓存@EnableCaching
 * 2.标注缓存注解@Cacheable/@CacheEvict/@CachePut
 * 默认使用的是ConcurrentMapCacheManager和ConcurrentMapCache，将数据保存在ConcurrentMap中
 * 开发中使用缓存中间件：Redis/Memcached/Ehcache
 * <p>
 * 整合Redis作为缓存：
 * Redis是一个开源的，内存中的数据结构存储系统，可以用作数据库、缓存和消息中间件
 * 1.安装Redis
 * 2.引入Redis的starter
 * 3.配置Redis
 * 4.使用缓存
 * 原理：CacheManager/Cache，缓存组件来给缓存中存取数据
 * 1.引入Redis的starter，容器中保存的是RedisCacheManager
 * 2.RedisCacheManager帮我们创建RedisCache来作为缓存组件，RedisCache通过操作Redis来缓存数据
 * 3.默认保存的数据都是Object，利用序列化机制，保存为JSON
 * 1).引入Redis的starter，默认的CacheManager变成了RedisCacheManager
 * 2).默认创建的RedisCacheManager操作Redis的时候使用的是RedisTemplate<Object, Object>
 * 3).RedisTemplate<Object, Object>默认使用JDK的序列化机制
 * 4.自定义CacheManager
 */
@MapperScan("com.atguigu.cache.mapper")
@SpringBootApplication
@EnableCaching
public class Springboot01CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot01CacheApplication.class, args);
    }

}
