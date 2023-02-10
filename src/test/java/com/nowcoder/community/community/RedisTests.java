package com.nowcoder.community.community;

import com.nowcoder.community.community.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStrings() {
        String testKey = "test:count";
        redisTemplate.opsForValue().set(testKey,1);
        System.out.println(redisTemplate.opsForValue().get(testKey));
        System.out.println(redisTemplate.opsForValue().increment(testKey));
        System.out.println(redisTemplate.opsForValue().decrement(testKey));
    }

    @Test
    public void testHash() {
        String testKey = "test:user";
        redisTemplate.opsForHash().put(testKey,"id",1);
        redisTemplate.opsForHash().put(testKey,"username","zhangsan");

        System.out.println(redisTemplate.opsForHash().get(testKey,"id"));
        System.out.println(redisTemplate.opsForHash().get(testKey,"username"));
    }
}
