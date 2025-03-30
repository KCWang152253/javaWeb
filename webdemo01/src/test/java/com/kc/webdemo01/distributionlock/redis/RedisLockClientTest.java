package com.kc.webdemo01.distributionlock.redis;

import com.kc.webdemo01.Webdemo01Application;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author KCWang
 * @version 1.0
 * @date 2025/3/30 下午5:19
 */
@Slf4j
@SpringBootTest(classes = Webdemo01Application.class)
@RunWith(SpringRunner.class)
public class RedisLockClientTest extends TestCase {


    @Autowired
    private RedisLockClient redisLockClient;


    @Test
    public void testLockSuccess() throws InterruptedException {
        Lock lock = redisLockClient.getLock("order:pay");
        try {
            boolean isLock = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!isLock) {
                log.warn("加锁失败");
                return;
            }
            TimeUnit.SECONDS.sleep(3);
            log.info("业务逻辑执行完成");
        } finally {
            lock.unlock();
        }

    }

}
