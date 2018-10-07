package com.atguigu.cache.service;

import com.atguigu.cache.bean.Department;
import com.atguigu.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "dept", cacheManager = "deptCacheManager")
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("deptCacheManager")
    @Autowired
    RedisCacheManager deptCacheManager;

    /*@Cacheable(cacheNames = "dept")
    public Department getDeptById(Integer id) {
        System.out.println("查询" + id + "号部门");
        Department department = departmentMapper.getDeptById(id);
        return department;
    }*/

    /**
     * 使用缓存管理器得到缓存，可以对缓存进行操作
     */
    public Department getDeptById(Integer id) {
        System.out.println("查询" + id + "号部门");
        Department department = departmentMapper.getDeptById(id);
        //获取缓存
        Cache cache = deptCacheManager.getCache("dept");
        //向缓存中添加数据
        cache.put(id, department);
        return department;
    }

}
