package com.atguigu.cache.service;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp", cacheManager = "empCacheManager")//抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * @Cacheable 将方法的返回值进行缓存，之后获取相同的数据时，直接从缓存中获取
     * CacheManager管理多个Cache组件，对缓存的真正CRUD在Cache组件中，每个组件都有自己唯一的名字
     * <p>
     * 原理：
     * 1.自动配置类：CacheAutoConfiguration
     * 2.缓存的配置类：
     * org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     * 3.哪个配置类默认生效：SimpleCacheConfiguration
     * 4.给容器中注册了一个CacheManager：ConcurrentMapCacheManager
     * 5.可以获取和创建ConcurrentMapCache类型的缓存组件，它的作用就是将数据保存在ConcurrentMap中
     * <p>
     * 运行流程：
     * 1.方法运行之前，先去查找Cache组件，按照cacheName指定的名字获取，第一次获取，如果没有Cache组件则会自动创建
     * 2.使用一个key去Cache中查找缓存的内容，key是按照某种策略生成的，默认使用SimpleKeyGenerator生成key
     * 3.没有查到缓存就调用目标方法
     * 4.将目标方法的返回值放进缓存中
     * <p>
     * 核心：
     * 1.使用CacheManager[ConcurrentMapCacheManager]按照名字得到Cache[ConcurrentMapCache]组件
     * 2.使用SimpleKeyGenerator生成key
     * <p>
     * 几个属性：
     * 1.cacheNames/value：指定缓存组件的名字
     * 2.key：指定缓存数据使用的key；默认使用方法参数的值，或者使用SpEL：#id，参数id的值；#a0/#p0/#root.args[0]，第一个参数的值
     * 3.keyGenerator：指定key生成器的组件id；key/keyGenerator二选一使用
     * 4.cacheManager：指定缓存管理器
     * 5.cacheResolver：指定获取解析器；cacheManager/cacheResolver二选一使用
     * 6.condition：指定符合条件的情况下使用缓存，condition = "#id > 0"
     * 7.unless：指定符合条件的情况下不使用缓存（不符合条件的情况下都使用缓存），可以获取到结果进行判断，unless = "#result == null"
     * 8.sync：指定是否使用异步模式
     */
    @Cacheable(value = "emp", keyGenerator = "myKeyGenerator")
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * @CachePut 既调用方法，又更新缓存数据；更新数据库同步更新缓存
     * 运行时机：
     * 1.先调用目标方法
     * 2.将目标方法的返回值缓存起来
     * <p>
     * 测试步骤：
     * 1.查询1号员工，查到的结果会放在缓存中
     * 2.之后查询还是之前的结果
     * 3.更新1号员工，方法返回值会放进缓存中，key是传入的Employee对象，value是返回的Employee对象
     * 4.查询1号员工，应该是更新后的员工，为什么是没更新前的？原因在于key不相同，若要保证正确更新对应缓存，就要使用正确的key
     * key = "#employee.id"，使用传入的Employee对象的id作为缓存的key
     * key = "#result.id"，使用返回的Employee对象的id作为缓存的key
     */
    @CachePut(value = "emp", key = "#employee.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("更新" + employee.getId() + "号员工");
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict 缓存清除
     * key：指定要清除的数据
     * allEntries = true：指定清除这个缓存中的所有数据
     * beforeInvocation = false：缓存的清除在方法之后执行，如果出现异常，缓存就不会清除
     * beforeInvocation = true：缓存的清除在方法之前执行，无论方法是否出现异常，缓存都会清除
     */
    @CacheEvict(value = "emp", key = "#id")
    public void deleteEmp(Integer id) {
        System.out.println("删除" + id + "号员工");
        employeeMapper.deleteEmpById(id);
    }

    /**
     * @Caching 定义复杂的缓存规则
     */
    @Caching(cacheable = {@Cacheable(value = "emp", key = "#lastName")}, put = {@CachePut(value = "emp", key = "#result.id"), @CachePut(value = "emp", key = "#result.email")})
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }

}
