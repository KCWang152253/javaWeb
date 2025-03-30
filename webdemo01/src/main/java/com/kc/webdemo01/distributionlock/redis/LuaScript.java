package com.kc.webdemo01.distributionlock.redis;

/**
 * @author KCWang
 * @version 1.0
 * @date 2025/3/30 下午5:00
 */
public class LuaScript {

    private LuaScript() {

    }

    /**
     * 分布式锁解锁脚本
     *
     * @return 当且仅当返回 `1`才表示加锁成功.
     */
    public static String unlockScript() {
        return "if (redis.call('exists', KEYS[1]) == 0) then " +
                "return nil;" +
                "end; "+
                "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                "   return redis.call('del',KEYS[1]);" +
                "else" +
                "   return 0;" +
                "end;";
    }
}