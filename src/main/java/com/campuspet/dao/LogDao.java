package com.campuspet.dao;

import com.campuspet.entity.Log;
import com.campuspet.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogDao {
    public void save(Log log) throws SQLException {
        String sql = "insert into system_log (operator_id, operator_name, operation_type, operation_content, ip_address, create_time) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, log.getOperatorId());
            statement.setString(2, log.getOperatorName());
            statement.setString(3, log.getOperationType());
            statement.setString(4, log.getOperationContent());
            statement.setString(5, log.getIpAddress());
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    public List<Log> findAll() throws SQLException {
        String sql = "select id, operator_id, operator_name, operation_type, operation_content, ip_address, create_time from system_log order by create_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Log> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapLog(resultSet));
            }
            return list;
        }
    }

    private Log mapLog(ResultSet resultSet) throws SQLException {
        Log log = new Log();
        log.setId(resultSet.getLong("id"));
        log.setOperatorId(resultSet.getLong("operator_id"));
        log.setOperatorName(resultSet.getString("operator_name"));
        log.setOperationType(resultSet.getString("operation_type"));
        log.setOperationContent(resultSet.getString("operation_content"));
        log.setIpAddress(resultSet.getString("ip_address"));
        log.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
        return log;
    }
}
