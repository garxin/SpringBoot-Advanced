package com.atguigu.cache;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;//操作键值对都是字符串的

    @Autowired
    RedisTemplate redisTemplate;//操作键值对都是对象的

    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;

    /**
     * Redis常见的五大数据类型：String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     * stringRedisTemplate.opsForValue()：用来操作String（字符串）
     * stringRedisTemplate.opsForList()：用来操作List（列表）
     * stringRedisTemplate.opsForSet()：用来操作Set（集合）
     * stringRedisTemplate.opsForHash()：用来操作Hash（散列）
     * stringRedisTemplate.opsForZSet()：用来操作ZSet（有序集合）
     */
    @Test
    public void test01() {
        stringRedisTemplate.opsForValue().append("msg", "hello");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);

        stringRedisTemplate.opsForList().leftPush("myList", "1");
        stringRedisTemplate.opsForList().leftPush("myList", "2");
    }

    //测试保存对象
    @Test
    public void test02() {
        Employee emp = employeeMapper.getEmpById(1);
        //默认如果保存对象，使用JDK序列化机制，序列化后的数据保存到Redis中
//        redisTemplate.opsForValue().set("emp-01", emp);
        //将数据以JSON的方式保存
        empRedisTemplate.opsForValue().set("emp-01", emp);
    }

    @Test
    public void contextLoads() {
        Employee emp = employeeMapper.getEmpById(1);
        System.out.println(emp);
    }

}
