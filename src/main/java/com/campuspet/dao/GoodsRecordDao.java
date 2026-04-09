package com.campuspet.dao;

import com.campuspet.entity.GoodsRecord;
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

public class GoodsRecordDao {
    public void save(GoodsRecord record) throws SQLException {
        String sql = "insert into goods_record (goods_id, goods_name, type, quantity, operator_id, operator_name, remark, create_time) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, record.getGoodsId());
            statement.setString(2, record.getGoodsName());
            statement.setString(3, record.getType());
            statement.setInt(4, record.getQuantity());
            statement.setLong(5, record.getOperatorId());
            statement.setString(6, record.getOperatorName());
            statement.setString(7, record.getRemark());
            statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    public GoodsRecord findById(Long id) throws SQLException {
        String sql = "select id, goods_id, goods_name, type, quantity, operator_id, operator_name, remark, create_time from goods_record where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapGoodsRecord(resultSet);
                }
                return null;
            }
        }
    }

    public List<GoodsRecord> findAll() throws SQLException {
        String sql = "select id, goods_id, goods_name, type, quantity, operator_id, operator_name, remark, create_time from goods_record order by create_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<GoodsRecord> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapGoodsRecord(resultSet));
            }
            return list;
        }
    }

    public List<GoodsRecord> findByGoodsId(Long goodsId) throws SQLException {
        String sql = "select id, goods_id, goods_name, type, quantity, operator_id, operator_name, remark, create_time from goods_record where goods_id = ? order by create_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<GoodsRecord> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapGoodsRecord(resultSet));
            }
            return list;
        }
    }

    public List<GoodsRecord> findRecent(int limit) throws SQLException {
        String sql = "select id, goods_id, goods_name, type, quantity, operator_id, operator_name, remark, create_time from goods_record order by create_time desc limit ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<GoodsRecord> list = new ArrayList<>();
                while (resultSet.next()) {
                    list.add(mapGoodsRecord(resultSet));
                }
                return list;
            }
        }
    }

    private GoodsRecord mapGoodsRecord(ResultSet resultSet) throws SQLException {
        GoodsRecord record = new GoodsRecord();
        record.setId(resultSet.getLong("id"));
        record.setGoodsId(resultSet.getLong("goods_id"));
        record.setGoodsName(resultSet.getString("goods_name"));
        record.setType(resultSet.getString("type"));
        record.setQuantity(resultSet.getInt("quantity"));
        record.setOperatorId(resultSet.getLong("operator_id"));
        record.setOperatorName(resultSet.getString("operator_name"));
        record.setRemark(resultSet.getString("remark"));
        record.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
        return record;
    }
}
