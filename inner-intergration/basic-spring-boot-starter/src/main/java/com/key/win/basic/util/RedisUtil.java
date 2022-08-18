package com.key.win.basic.util;

/*
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisUtil {

    public static Cursor<String> scan(RedisTemplate redisTemplate, String match, int count) {
        ScanOptions scanOptions = ScanOptions.scanOptions().match(match).count(count).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        return (Cursor<String>) redisTemplate.executeWithStickyConnection((RedisCallback) redisConnection ->
                new ConvertingCursor<>(redisConnection.scan(scanOptions), redisSerializer::deserialize));
    }
}
*/
