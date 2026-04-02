package com.campuspet.dao;

import com.campuspet.entity.User;
import com.campuspet.enums.RoleType;
import com.campuspet.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserDao {
    public User findByUsername(String username) throws SQLException {
        String sql = "select id, username, password, phone, role, status from users where username = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
                return null;
            }
        }
    }

    public User login(String username, String password, RoleType roleType) throws SQLException {
        String sql = "select id, username, password, phone, role, status from users where username = ? and password = ? and role = ? and status = '正常'";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, roleType.getDisplayName());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
                return null;
            }
        }
    }

    public void save(User user) throws SQLException {
        String sql = "insert into users (username, password, phone, role, status, create_time) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getRole().getDisplayName());
            statement.setString(5, user.getStatus());
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setPhone(resultSet.getString("phone"));
        user.setRole(RoleType.fromDisplayName(resultSet.getString("role")));
        user.setStatus(resultSet.getString("status"));
        return user;
    }
}
