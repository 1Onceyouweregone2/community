package com.nowcoder.community.community;

import com.nowcoder.community.community.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
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

    //统计海量的重复数据
    @Test
    public void testHyperLogLog() {
        String redisKey = "test:hll";
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey,i);
        }
        for (int i = 0; i < 100000; i++) {
            int v = (int)(Math.random() + i);
            redisTemplate.opsForHyperLogLog().add(redisKey,i);
        }
        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKey));
    }

    //3组数据合并，并统计合并后的重复数据
    @Test
    public void testHyperLogLog1() {
        String redisKey1 = "test:hll:01";
        for (int i = 0; i < 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey1,i);
        }
        String redisKey2 = "test:hll:02";
        for (int i = 5000; i < 15000; i++) {
            int v = (int)(Math.random() + i);
            redisTemplate.opsForHyperLogLog().add(redisKey2,i);
        }
        String redisKey3 = "test:hll:03";
        for (int i = 10000; i < 20000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey3,i);
        }

        String unionKey = "test:hll:union";
        redisTemplate.opsForHyperLogLog().union(unionKey,redisKey1,redisKey2,redisKey3);
        System.out.println(redisTemplate.opsForHyperLogLog().size(unionKey));
    }

    //统计布尔值
    @Test
    public void testBitMap() {
        String redisKey = "test:bm:01";
        redisTemplate.opsForValue().setBit(redisKey,1,true);
        redisTemplate.opsForValue().setBit(redisKey,4,true);
        redisTemplate.opsForValue().setBit(redisKey,7,true);

        //获取
        System.out.println(redisTemplate.opsForValue().getBit(redisKey,0));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey,1));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey,2));

        //查询个数
        Object o = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(redisKey.getBytes());
            }
        });

        System.out.println(o);
    }
}
