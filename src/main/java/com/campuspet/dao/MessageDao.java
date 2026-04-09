package com.campuspet.dao;

import com.campuspet.entity.Message;
import com.campuspet.entity.User;
import com.campuspet.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    public void save(Message message) throws SQLException {
        String sql = "insert into message (user_id, title, content, type, is_read, send_time) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, message.getUserId());
            statement.setString(2, message.getTitle());
            statement.setString(3, message.getContent());
            statement.setString(4, message.getType());
            statement.setString(5, "否");
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    public void updateIsRead(Long id, String isRead) throws SQLException {
        String sql = "update message set is_read = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, isRead);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    public void markAllAsRead(Long userId) throws SQLException {
        String sql = "update message set is_read = '是' where user_id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        }
    }

    public Message findById(Long id) throws SQLException {
        String sql = "select id, user_id, title, content, type, is_read, send_time from message where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapMessage(resultSet);
                }
                return null;
            }
        }
    }

    public List<Message> findByUserId(Long userId) throws SQLException {
        String sql = "select id, user_id, title, content, type, is_read, send_time from message where user_id = ? order by send_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Message> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapMessage(resultSet));
            }
            return list;
        }
    }

    public List<Message> findUnreadByUserId(Long userId) throws SQLException {
        String sql = "select id, user_id, title, content, type, is_read, send_time from message where user_id = ? and is_read = '否' order by send_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Message> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapMessage(resultSet));
            }
            return list;
        }
    }

    public int countUnreadByUserId(Long userId) throws SQLException {
        String sql = "select count(*) as count from message where user_id = ? and is_read = '否'";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
            return 0;
        }
    }

    private Message mapMessage(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setId(resultSet.getLong("id"));
        message.setUserId(resultSet.getLong("user_id"));
        message.setTitle(resultSet.getString("title"));
        message.setContent(resultSet.getString("content"));
        message.setType(resultSet.getString("type"));
        message.setIsRead(resultSet.getString("is_read"));
        message.setSendTime(resultSet.getTimestamp("send_time").toLocalDateTime());
        return message;
    }
}
