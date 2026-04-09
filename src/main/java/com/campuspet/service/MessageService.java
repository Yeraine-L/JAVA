package com.campuspet.service;

import com.campuspet.dao.MessageDao;
import com.campuspet.entity.Message;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
    private MessageDao dao = new MessageDao();

    public void sendMessage(Long userId, String title, String content, String type) {
        try {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("标题不能为空");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("内容不能为空");
            }

            Message message = new Message();
            message.setUserId(userId);
            message.setTitle(title);
            message.setContent(content);
            message.setType(type);
            message.setSendTime(java.time.LocalDateTime.now());

            dao.save(message);
        } catch (SQLException e) {
            throw new IllegalStateException("发送消息失败");
        }
    }

    public Message getDetail(Long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("获取消息详情失败");
        }
    }

    public List<Message> getMyMessages(Long userId) {
        try {
            return dao.findByUserId(userId);
        } catch (SQLException e) {
            throw new IllegalStateException("获取消息列表失败");
        }
    }

    public List<Message> getUnreadMessages(Long userId) {
        try {
            return dao.findUnreadByUserId(userId);
        } catch (SQLException e) {
            throw new IllegalStateException("获取未读消息失败");
        }
    }

    public int getUnreadCount(Long userId) {
        try {
            return dao.countUnreadByUserId(userId);
        } catch (SQLException e) {
            throw new IllegalStateException("获取未读消息数量失败");
        }
    }

    public void markAsRead(Long id) {
        try {
            Message message = dao.findById(id);
            if (message == null) {
                throw new IllegalArgumentException("消息不存在");
            }
            dao.updateIsRead(id, "是");
        } catch (SQLException e) {
            throw new IllegalStateException("标记消息已读失败");
        }
    }

    public void markAllAsRead(Long userId) {
        try {
            dao.markAllAsRead(userId);
        } catch (SQLException e) {
            throw new IllegalStateException("全部标记已读失败");
        }
    }
}
