package com.campuspet.dao;

import com.campuspet.entity.Goods;
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

public class GoodsDao {
    public void save(Goods goods) throws SQLException {
        String sql = "insert into goods (name, type, unit, total_quantity, min_quantity, create_time) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, goods.getName());
            statement.setString(2, goods.getType());
            statement.setString(3, goods.getUnit());
            statement.setInt(4, goods.getTotalQuantity());
            statement.setInt(5, goods.getMinQuantity());
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
        }
    }

    public void updateQuantity(Long id, int quantity) throws SQLException {
        String sql = "update goods set total_quantity = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quantity);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    public Goods findById(Long id) throws SQLException {
        String sql = "select id, name, type, unit, total_quantity, min_quantity, create_time from goods where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapGoods(resultSet);
                }
                return null;
            }
        }
    }

    public List<Goods> findAll() throws SQLException {
        String sql = "select id, name, type, unit, total_quantity, min_quantity, create_time from goods order by create_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Goods> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapGoods(resultSet));
            }
            return list;
        }
    }

    public List<Goods> findByType(String type) throws SQLException {
        String sql = "select id, name, type, unit, total_quantity, min_quantity, create_time from goods where type = ? order by create_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Goods> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapGoods(resultSet));
            }
            return list;
        }
    }

    public List<Goods> findLowStock() throws SQLException {
        String sql = "select id, name, type, unit, total_quantity, min_quantity, create_time from goods where total_quantity <= min_quantity order by total_quantity asc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Goods> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(mapGoods(resultSet));
            }
            return list;
        }
    }

    private Goods mapGoods(ResultSet resultSet) throws SQLException {
        Goods goods = new Goods();
        goods.setId(resultSet.getLong("id"));
        goods.setName(resultSet.getString("name"));
        goods.setType(resultSet.getString("type"));
        goods.setUnit(resultSet.getString("unit"));
        goods.setTotalQuantity(resultSet.getInt("total_quantity"));
        goods.setMinQuantity(resultSet.getInt("min_quantity"));
        goods.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
        return goods;
    }

    public void update(Goods goods) throws SQLException {
        String sql = "update goods set name = ?, type = ?, unit = ?, min_quantity = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, goods.getName());
            statement.setString(2, goods.getType());
            statement.setString(3, goods.getUnit());
            statement.setInt(4, goods.getMinQuantity());
            statement.setLong(5, goods.getId());
            statement.executeUpdate();
        }
    }
}
