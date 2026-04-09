package com.campuspet.dao;

import com.campuspet.entity.FeedPoint;
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

public class FeedPointDao {
    public void save(FeedPoint feedPoint) throws SQLException {
        String sql = "insert into feed_point (volunteer_id, volunteer_name, location, area, description, status, apply_time) values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, feedPoint.getVolunteerId());
            statement.setString(2, feedPoint.getVolunteerName());
            statement.setString(3, feedPoint.getLocation());
            statement.setString(4, feedPoint.getArea());
            statement.setString(5, feedPoint.getDescription());
            statement.setString(6, "待审核");
            statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    public void updateStatus(Long id, String status, String opinion) throws SQLException {
        String sql = "update feed_point set status = ?, review_time = ?, review_opinion = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(3, opinion);
            statement.setLong(4, id);
            statement.executeUpdate();
        }
    }

    public FeedPoint findById(Long id) throws SQLException {
        String sql = "select id, volunteer_id, volunteer_name, location, area, description, status, apply_time, review_time, review_opinion from feed_point where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapFeedPoint(resultSet);
                }
                return null;
            }
        }
    }

    public List<FeedPoint> findAll() throws SQLException {
        String sql = "select id, volunteer_id, volunteer_name, location, area, description, status, apply_time, review_time, review_opinion from feed_point order by apply_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<FeedPoint> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapFeedPoint(resultSet));
            }
            return list;
        }
    }

    public List<FeedPoint> findByVolunteerId(Long volunteerId) throws SQLException {
        String sql = "select id, volunteer_id, volunteer_name, location, area, description, status, apply_time, review_time, review_opinion from feed_point where volunteer_id = ? order by apply_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<FeedPoint> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapFeedPoint(resultSet));
            }
            return list;
        }
    }

    public List<FeedPoint> findByStatus(String status) throws SQLException {
        String sql = "select id, volunteer_id, volunteer_name, location, area, description, status, apply_time, review_time, review_opinion from feed_point where status = ? order by apply_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<FeedPoint> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapFeedPoint(resultSet));
            }
            return list;
        }
    }

    private FeedPoint mapFeedPoint(ResultSet resultSet) throws SQLException {
        FeedPoint feedPoint = new FeedPoint();
        feedPoint.setId(resultSet.getLong("id"));
        feedPoint.setVolunteerId(resultSet.getLong("volunteer_id"));
        feedPoint.setVolunteerName(resultSet.getString("volunteer_name"));
        feedPoint.setLocation(resultSet.getString("location"));
        feedPoint.setArea(resultSet.getString("area"));
        feedPoint.setDescription(resultSet.getString("description"));
        feedPoint.setStatus(resultSet.getString("status"));
        feedPoint.setApplyTime(resultSet.getTimestamp("apply_time").toLocalDateTime());
        if (resultSet.getTimestamp("review_time") != null) {
            feedPoint.setReviewTime(resultSet.getTimestamp("review_time").toLocalDateTime());
        }
        feedPoint.setReviewOpinion(resultSet.getString("review_opinion"));
        return feedPoint;
    }

    public void update(FeedPoint feedPoint) throws SQLException {
        String sql = "update feed_point set location = ?, area = ?, description = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, feedPoint.getLocation());
            statement.setString(2, feedPoint.getArea());
            statement.setString(3, feedPoint.getDescription());
            statement.setLong(4, feedPoint.getId());
            statement.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "delete from feed_point where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
