package com.kc.webdemo01.distributionlock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author KCWang
 * @version 1.0
 * @date 2025/3/30 下午5:01
 */
@Component
public class RedisLockClient {

    @Autowired
    private StringRedisTemplate redisTemplate;


    public Lock getLock(String name) {
        return new DistributedLock(name, redisTemplate);
    }

}
