package com.nowcoder.community.community.service;

import com.nowcoder.community.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param userId 进行点赞操作的用户id
     * @param entityType 实体类型，0帖子/1回复
     * @param entityId 帖子id
     * @param entityUserId 帖子作者id
     */
    public void like(int userId, int entityType, int entityId, int entityUserId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //帖子点赞
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                //用户收到的点赞
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);

                //帖子点赞的集合当中是否有当前id
                Boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

                //开启redis事务
                operations.multi();

                //userid存在了，进行点赞操作的用户id已经点赞过了
                if (isMember) {
                    //取消点赞
                    operations.opsForSet().remove(entityLikeKey, userId);
                    //entityUserId即帖子作者id的获赞数-1
                    operations.opsForValue().decrement(userLikeKey);
                } else {//userid不存在，进行点赞操作的用户id没有赞过
                    operations.opsForSet().add(entityLikeKey, userId);
                    //entityUserId即帖子作者id的获赞数+1
                    operations.opsForValue().increment(userLikeKey);
                }
                //执行redis事务
                return operations.exec();
            }
        });
    }

    /**
     * 查询某实体点赞数量
     * @param entityType
     * @param entityId
     * @return
     */
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * 查询某人对某实体的点赞状态，1点赞，0未点赞
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    /**
     * 查询某个用户获得赞的数量
     * @param userId
     * @return
     */
    public long findUserLikeCount(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();
    }
}
