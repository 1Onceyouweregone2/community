package com.nowcoder.community.community.dao;

import com.nowcoder.community.community.entity.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageMapper {

    //查询当前用户的会话列表，针对每个会话只返回一条私信
    List<Message> selectConversations(int userId, int offset, int limit);

    //查询当前会话数量
    int selectConversationCount(int userId);

    //查询某个会话的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    //查询某个会话的私信数量
    int selectLLetterCount(String conversationId);

    //查询未读私信数量
    int selectLetterUnreadCount(int userId, String conversationId);

    //添加消息
    int insertMessage(Message message);

    //修改消息的状态
    int updateStatus(List<Integer> ids, int status);

    //查询某个主题下的最新的通知
    Message selectLatestNotice(int userId, String topic);

    //查询某个主题下的通知数量
    int selectNoticeCount(int userId, String topic);

    //查询未读通知数量
    int selectNoticeUnreadCount(int userId, String topic);

    //查询某个主题包含的通知列表
    List<Message> selectNotices(int userId, String topic, int offset, int limit);
}
