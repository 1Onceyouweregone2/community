package com.nowcoder.community.community;

import com.nowcoder.community.community.dao.DiscussPostMapper;
import com.nowcoder.community.community.dao.LoginTicketMapper;
import com.nowcoder.community.community.dao.MessageMapper;
import com.nowcoder.community.community.dao.UserMapper;
import com.nowcoder.community.community.entity.DiscussPost;
import com.nowcoder.community.community.entity.LoginTicket;
import com.nowcoder.community.community.entity.Message;
import com.nowcoder.community.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        User liubei = userMapper.selectByName("liubei");
        System.out.println(liubei);

        User user1 = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user1);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("t");
    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(0, 0, 10);
        for(DiscussPost post : discussPosts){
            System.out.println(post);
        }
        int i = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(i);
    }

    @Test
    public void testLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setStatus(0);
        loginTicket.setTicket("abc");
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket() {
        LoginTicket abc = loginTicketMapper.selectByTicket("abc");
        System.out.println(abc);

        loginTicketMapper.updateStatus("abc", 0);
        abc = loginTicketMapper.selectByTicket("abc");
        System.out.println(abc);
    }

    @Test
    public void selectLetters() {
        List<Message> messages = messageMapper.selectConversations(111, 0, 10);
        for(Message msg : messages) {
            System.out.println(msg);
        }

        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);

        List<Message> messages1 = messageMapper.selectLetters("111_112", 0, 10);
        for(Message msg : messages1) {
            System.out.println(msg);
        }

        int count1 = messageMapper.selectLLetterCount("111_112");
        System.out.println(count1);

        int count2 = messageMapper.selectLetterUnreadCount(111, "111_112");
        System.out.println(count2);
    }
}
