package com.witboot.infrastructure.common.utils;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

=======
>>>>>>> ca56e17 (日志等级)
/**
 * Redis工具类
 *
 * @author sunxiaozhi
 */
<<<<<<< HEAD
@Component
public class RedisUtil {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存数据
     *
     * @param key   键
     * @param value 值
     */
    public <T> void setCache(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public <T> T getCache(String key) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     * @return 结果
     */
    public boolean deleteCache(String key) {
        return redisTemplate.delete(key);
    }
=======
public class RedisUtil {
>>>>>>> ca56e17 (日志等级)
}