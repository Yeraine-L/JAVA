package com.campuspet.dao;

import com.campuspet.entity.ExceptionReport;
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

public class ExceptionReportDao {
    public void save(ExceptionReport report) throws SQLException {
        String sql = "insert into exception_report (volunteer_id, volunteer_name, location, area, type, description, photo, status, create_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, report.getVolunteerId());
            statement.setString(2, report.getVolunteerName());
            statement.setString(3, report.getLocation());
            statement.setString(4, report.getArea());
            statement.setString(5, report.getType());
            statement.setString(6, report.getDescription());
            statement.setString(7, report.getPhoto());
            statement.setString(8, "待处理");
            statement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    public void updateStatus(Long id, String status, String opinion) throws SQLException {
        String sql = "update exception_report set status = ?, handle_opinion = ?, handle_time = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setString(2, opinion);
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(4, id);
            statement.executeUpdate();
        }
    }

    public ExceptionReport findById(Long id) throws SQLException {
        String sql = "select id, volunteer_id, volunteer_name, location, area, type, description, photo, status, handle_opinion, handle_time, create_time from exception_report where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapExceptionReport(resultSet);
                }
                return null;
            }
        }
    }

    public List<ExceptionReport> findAll() throws SQLException {
        String sql = "select id, volunteer_id, volunteer_name, location, area, type, description, photo, status, handle_opinion, handle_time, create_time from exception_report order by create_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<ExceptionReport> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapExceptionReport(resultSet));
            }
            return list;
        }
    }

    public List<ExceptionReport> findByVolunteerId(Long volunteerId) throws SQLException {
        String sql = "select id, volunteer_id, volunteer_name, location, area, type, description, photo, status, handle_opinion, handle_time, create_time from exception_report where volunteer_id = ? order by create_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<ExceptionReport> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapExceptionReport(resultSet));
            }
            return list;
        }
    }

    public List<ExceptionReport> findByStatus(String status) throws SQLException {
        String sql = "select id, volunteer_id, volunteer_name, location, area, type, description, photo, status, handle_opinion, handle_time, create_time from exception_report where status = ? order by create_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<ExceptionReport> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapExceptionReport(resultSet));
            }
            return list;
        }
    }

    private ExceptionReport mapExceptionReport(ResultSet resultSet) throws SQLException {
        ExceptionReport report = new ExceptionReport();
        report.setId(resultSet.getLong("id"));
        report.setVolunteerId(resultSet.getLong("volunteer_id"));
        report.setVolunteerName(resultSet.getString("volunteer_name"));
        report.setLocation(resultSet.getString("location"));
        report.setArea(resultSet.getString("area"));
        report.setType(resultSet.getString("type"));
        report.setDescription(resultSet.getString("description"));
        report.setPhoto(resultSet.getString("photo"));
        report.setStatus(resultSet.getString("status"));
        report.setHandleOpinion(resultSet.getString("handle_opinion"));
        if (resultSet.getTimestamp("handle_time") != null) {
            report.setHandleTime(resultSet.getTimestamp("handle_time").toLocalDateTime());
        }
        report.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
        return report;
    }
}
