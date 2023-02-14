package com.nowcoder.community.community.quartz;

import com.nowcoder.community.community.entity.DiscussPost;
import com.nowcoder.community.community.service.CommentService;
import com.nowcoder.community.community.service.DiscussPostService;
import com.nowcoder.community.community.service.ElasticSearchService;
import com.nowcoder.community.community.service.LikeService;
import com.nowcoder.community.community.util.CommunityConstant;
import com.nowcoder.community.community.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class PostScoreRefreshJob implements Job, CommunityConstant {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    //平台纪元
    private static final Date epoch;

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-12-01 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException("初始化平台纪元失败！", e);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScore();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);
        if (operations.size() == 0) {
            log.error("[quartz任务取消]没有需要刷新分数的帖子");
            return;
        }
        log.info("[quartz任务开始]正在刷新帖子分数：" + operations.size());
        while (operations.size() > 0) {
            this.refresh((Integer) operations.pop());
        }
        log.info("[quartz任务结束]刷新帖子分数完毕！");
    }

    private void refresh(int postId) {
        DiscussPost post = discussPostService.findDiscussPostById(postId);
        if (post == null) {
            log.error("帖子[id={}]不存在！", postId);
            return;
        }
        //是否加精
        boolean wonderful = post.getStatus() == 1;
        //评论数量
        int commentCount = post.getCommentCount();
        //点赞数量
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, postId);

        //计算权重
        double w = (wonderful ? 75 : 0) + commentCount * 10 + likeCount * 2;

        //score=权重+距离天数
        double score = Math.log10(Math.max(w, 1)) + (post.getCreateTime().getTime() - epoch.getTime()) / (3600 * 24 * 1000);

        //更新分数
        discussPostService.updateScore(postId, score);

        //同步es的数据
        post.setScore(score);
        elasticSearchService.saveDiscussPost(post);
    }
}
