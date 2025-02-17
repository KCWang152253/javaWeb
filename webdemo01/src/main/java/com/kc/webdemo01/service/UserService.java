package com.kc.webdemo01.service;

/**
 * @author
 * @version 1.0
 * @date 2025/2/16 下午11:53
 */

import com.kc.webdemo01.bean.UserData;
import com.kc.webdemo01.mapper.UserDataMapper;
import com.kc.webdemo01.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 缓存机制是作用到Service层的，
 * 而dao或者repository层缓存用注解,用key的话它会认定为null。
 * 要用KeyGenerator来提前生成key的生成策略
 *
 * @author KCWang
 */

/**
 *
 @CacheConfig: 类级别的注解：如果我们在此注解中定义cacheNames，则此类中的所有方法上 @Cacheable的cacheNames默认都是此值。当然@Cacheable也可以重定义cacheNames的值
 */
@Service
@CacheConfig(cacheNames = "UserData")
public class UserService {

    @Resource
    private UserDataMapper userDao;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * @Cacheable的属性的意义 cacheNames：指定缓存的名称
     * key：定义组成的key值，如果不定义，则使用全部的参数计算一个key值。可以使用spring El表达式
     * keyGenerator：定义key生成的类，和key的不能同时存在
     * sync：如果设置sync=true：a. 如果缓存中没有数据，多个线程同时访问这个方法，则只有一个方法会执行到方法，其它方法需要等待; b. 如果缓存中已经有数据，则多个线程可以同时从缓存中获取数据
     * condition和unless 只满足特定条件才进行缓存：
     * <p>
     * 1. condition: 在执行方法前，condition的值为true，则缓存数据
     * 2.unless ：在执行方法后，判断unless ，如果值为true，则不缓存数据
     * 3.conditon和unless可以同时使用，则此时只缓存同时满足两者的记录
     */
    @Cacheable(value = "UserData")
    public List<UserData> findList() {
        return userDao.queryUserList();
    }

    @Cacheable(cacheNames = "UserData", key = "#id")
    public UserData findById(Integer id) {
//        redisUtil.set(id.toString(),userDao.queryUserById(id).toString());
//        Object o = redisUtil.get(id.toString());
        return userDao.queryUserById(id);
    }

    /**
     * acheNames 设置缓存的值
     * key：指定缓存的key，这是指参数id值。 key可以使用spEl表达式,也可以是指定对象的成员变量
     *
     * @param UserData
     */
    @CachePut(cacheNames = "UserData", key = "#UserData.id")
    public void AddUser(UserData UserData) {
        userDao.addUser(UserData);
    }


    /**
     * 删除缓存
     * allEntries = true: 清空缓存book1里的所有值
     * allEntries = false: 默认值，此时只删除key对应的值
     * 如果指定为 true，则方法调用后将立即清空所有缓存
     */
    @CacheEvict(key = "#id", allEntries = true)
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }


    /**
     * 每次执行都会执行方法，无论缓存里是否有值，同时使用新的返回值的替换缓存中的值。这里不同@Cacheable：@Cacheable如果缓存没有值，从则执行方法并缓存数据，如果缓存有值，则从缓存中获取值
     */
    @CachePut(cacheNames = "UserData", key = "#UserData.id")
    public void updatePwdById(UserData UserData) {
        userDao.updateUser(UserData);
    }

    /**
     * 条件缓存：
     * 只有满足condition的请求才可以进行缓存，如果不满足条件，则跟方法没有@Cacheable注解的方法一样
     * 如下面只有id < 3才进行缓存
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "UserData", condition = "T(java.lang.Integer).parseInt(#id) < 3 ")
    public UserData queryUserByIdCondition(String id) {
        return userDao.queryUserById(Integer.parseInt(id));
    }

}
