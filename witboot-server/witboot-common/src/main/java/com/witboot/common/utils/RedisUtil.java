package com.witboot.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * 提供对 Redis 常见数据结构（String、Hash、List、Set、ZSet）的操作封装，
 * 简化 Redis 的使用，提升开发效率。
 *
 * @author sunxiaozhi
 */
@Component
public class RedisUtil {

    /**
     * 自动注入 RedisTemplate 实例，用于操作 Redis 数据库。
     */
    @Autowired
    private RedisTemplate redisTemplate;

    // ==================== String 操作 ====================

    /**
     * 缓存数据到 Redis 中。
     *
     * @param key   键
     * @param value 值
     * @param <T>   泛型类型
     */
    public <T> void setCache(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 从 Redis 中获取缓存数据。
     *
     * @param key 键
     * @param <T> 泛型类型
     * @return 缓存的值，若不存在则返回 null
     */
    public <T> T getCache(String key) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 删除 Redis 中的缓存数据。
     *
     * @param key 键
     * @return 是否删除成功
     */
    public boolean deleteCache(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 缓存数据并设置过期时间（单位：秒）。
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间（秒）
     * @param <T>     泛型类型
     */
    public <T> void setCacheWithExpire(String key, T value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    // ==================== Hash 操作 ====================

    /**
     * 向指定 key 的哈希表中添加键值对。
     *
     * @param key     哈希表的 key
     * @param hashKey 哈希表中的字段名
     * @param value   字段值
     */
    public void setHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 从指定 key 的哈希表中获取字段值。
     *
     * @param key     哈希表的 key
     * @param hashKey 字段名
     * @return 字段值，若不存在则返回 null
     */
    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除哈希表中的某个字段。
     *
     * @param key     哈希表的 key
     * @param hashKey 要删除的字段名
     */
    public void deleteHash(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    // ==================== List 操作 ====================

    /**
     * 将元素推入列表左侧（头部插入）。
     *
     * @param key   列表的 key
     * @param value 元素值
     */
    public void pushToList(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 从列表右侧弹出一个元素（尾部移除）。
     *
     * @param key 列表的 key
     * @return 弹出的元素值，若列表为空则返回 null
     */
    public Object popFromList(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 获取列表指定范围内的元素。
     *
     * @param key   列表的 key
     * @param start 起始索引（包含）
     * @param end   结束索引（包含）
     * @return 指定范围内的元素列表
     */
    public List<Object> getListRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // ==================== Set 操作 ====================

    /**
     * 向集合中添加多个元素。
     *
     * @param key    集合的 key
     * @param values 要添加的元素数组
     */
    public void addToSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 判断元素是否存在于集合中。
     *
     * @param key   集合的 key
     * @param value 要检查的元素
     * @return 若存在返回 true，否则返回 false
     */
    public boolean isMemberOfSet(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取集合中的所有成员。
     *
     * @param key 集合的 key
     * @return 集合中的所有成员
     */
    public Set<Object> getSetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // ==================== ZSet 操作 ====================

    /**
     * 向有序集合中添加元素并设置分数。
     *
     * @param key   有序集合的 key
     * @param score 分数
     * @param value 元素值
     */
    public void addWithScoreToZSet(String key, double score, Object value) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 根据分数范围获取有序集合中的元素。
     *
     * @param key       有序集合的 key
     * @param minScore  最小分数（包含）
     * @param maxScore  最大分数（包含）
     * @return 符合条件的元素集合
     */
    public Set<Object> getZSetRangeByScore(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
    }
}
