package com.kc.webdemo01.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/17 下午10:16
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    private final RedisTemplate redisTemplate;

    public RedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("save")
    public Boolean save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return Boolean.TRUE;
    }

}
