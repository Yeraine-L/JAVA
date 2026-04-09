package com.campuspet.dao;

import com.campuspet.entity.Announcement;
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

public class AnnouncementDao {
    public void save(Announcement announcement) throws SQLException {
        String sql = "insert into announcement (title, content, publisher_id, publisher_name, publish_time) values (?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, announcement.getTitle());
            statement.setString(2, announcement.getContent());
            statement.setLong(3, announcement.getPublisherId());
            statement.setString(4, announcement.getPublisherName());
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    public void update(Announcement announcement) throws SQLException {
        String sql = "update announcement set title = ?, content = ?, update_time = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, announcement.getTitle());
            statement.setString(2, announcement.getContent());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(4, announcement.getId());
            statement.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "delete from announcement where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Announcement findById(Long id) throws SQLException {
        String sql = "select id, title, content, publisher_id, publisher_name, publish_time, update_time from announcement where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAnnouncement(resultSet);
                }
                return null;
            }
        }
    }

    public List<Announcement> findAll() throws SQLException {
        String sql = "select id, title, content, publisher_id, publisher_name, publish_time, update_time from announcement order by publish_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Announcement> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapAnnouncement(resultSet));
            }
            return list;
        }
    }

    public List<Announcement> findRecent(int limit) throws SQLException {
        String sql = "select id, title, content, publisher_id, publisher_name, publish_time, update_time from announcement order by publish_time desc limit ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Announcement> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(mapAnnouncement(resultSet));
                }
                return list;
            }
        }
    }

    private Announcement mapAnnouncement(ResultSet resultSet) throws SQLException {
        Announcement announcement = new Announcement();
        announcement.setId(resultSet.getLong("id"));
        announcement.setTitle(resultSet.getString("title"));
        announcement.setContent(resultSet.getString("content"));
        announcement.setPublisherId(resultSet.getLong("publisher_id"));
        announcement.setPublisherName(resultSet.getString("publisher_name"));
        announcement.setPublishTime(resultSet.getTimestamp("publish_time").toLocalDateTime());
        if (resultSet.getTimestamp("update_time") != null) {
            announcement.setUpdateTime(resultSet.getTimestamp("update_time").toLocalDateTime());
        }
        return announcement;
    }
}
