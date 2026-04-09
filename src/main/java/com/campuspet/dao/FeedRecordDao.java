package com.campuspet.dao;

import com.campuspet.entity.FeedRecord;
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

public class FeedRecordDao {
    public void save(FeedRecord feedRecord) throws SQLException {
        String sql = "insert into feed_record (feed_point_id, volunteer_id, volunteer_name, pet_count, food_amount, food_type, feed_time, create_time) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, feedRecord.getFeedPointId());
            statement.setLong(2, feedRecord.getVolunteerId());
            statement.setString(3, feedRecord.getVolunteerName());
            statement.setInt(4, feedRecord.getPetCount());
            statement.setBigDecimal(5, feedRecord.getFoodAmount());
            statement.setString(6, feedRecord.getFoodType());
            statement.setTimestamp(7, Timestamp.valueOf(feedRecord.getFeedTime()));
            statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    public FeedRecord findById(Long id) throws SQLException {
        String sql = "select id, feed_point_id, volunteer_id, volunteer_name, pet_count, food_amount, food_type, feed_time, create_time from feed_record where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapFeedRecord(resultSet);
                }
                return null;
            }
        }
    }

    public List<FeedRecord> findByVolunteerId(Long volunteerId) throws SQLException {
        String sql = "select id, feed_point_id, volunteer_id, volunteer_name, pet_count, food_amount, food_type, feed_time, create_time from feed_record where volunteer_id = ? order by feed_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<FeedRecord> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapFeedRecord(resultSet));
            }
            return list;
        }
    }

    public List<FeedRecord> findByFeedPointId(Long feedPointId) throws SQLException {
        String sql = "select id, feed_point_id, volunteer_id, volunteer_name, pet_count, food_amount, food_type, feed_time, create_time from feed_record where feed_point_id = ? order by feed_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<FeedRecord> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapFeedRecord(resultSet));
            }
            return list;
        }
    }

    public List<FeedRecord> findRecent(int limit) throws SQLException {
        String sql = "select id, feed_point_id, volunteer_id, volunteer_name, pet_count, food_amount, food_type, feed_time, create_time from feed_record order by feed_time desc limit ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<FeedRecord> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(mapFeedRecord(resultSet));
                }
                return list;
            }
        }
    }

    private FeedRecord mapFeedRecord(ResultSet resultSet) throws SQLException {
        FeedRecord feedRecord = new FeedRecord();
        feedRecord.setId(resultSet.getLong("id"));
        feedRecord.setFeedPointId(resultSet.getLong("feed_point_id"));
        feedRecord.setVolunteerId(resultSet.getLong("volunteer_id"));
        feedRecord.setVolunteerName(resultSet.getString("volunteer_name"));
        feedRecord.setPetCount(resultSet.getInt("pet_count"));
        feedRecord.setFoodAmount(resultSet.getBigDecimal("food_amount"));
        feedRecord.setFoodType(resultSet.getString("food_type"));
        feedRecord.setFeedTime(resultSet.getTimestamp("feed_time").toLocalDateTime());
        feedRecord.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
        return feedRecord;
    }
}
