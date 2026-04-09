package com.campuspet.dao;

import com.campuspet.entity.AdoptApply;
import com.campuspet.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdoptApplyDao {
    public void save(AdoptApply apply) throws SQLException {
        String sql = "insert into adopt_apply (user_id, pet_id, applicant_name, phone, address, experience, reason, plan, apply_time, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, apply.getUserId());
            statement.setLong(2, apply.getPetId());
            statement.setString(3, apply.getApplicantName());
            statement.setString(4, apply.getPhone());
            statement.setString(5, apply.getAddress());
            statement.setString(6, apply.getExperience());
            statement.setString(7, apply.getReason());
            statement.setString(8, apply.getPlan());
            statement.setTimestamp(9, java.sql.Timestamp.valueOf(apply.getApplyTime()));
            statement.setString(10, "待审核");
            statement.executeUpdate();
        }
    }

    public List<AdoptApply> findByUserId(Long userId) throws SQLException {
        String sql = "select * from adopt_apply where user_id = ? order by apply_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<AdoptApply> applies = new ArrayList<>();
                while (resultSet.next()) {
                    applies.add(mapApply(resultSet));
                }
                return applies;
            }
        }
    }

    public AdoptApply findById(Long id) throws SQLException {
        String sql = "select * from adopt_apply where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapApply(resultSet);
                }
                return null;
            }
        }
    }

    public List<AdoptApply> findAll() throws SQLException {
        String sql = "select * from adopt_apply order by apply_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<AdoptApply> applies = new ArrayList<>();
            while (resultSet.next()) {
                applies.add(mapApply(resultSet));
            }
            return applies;
        }
    }

    public List<AdoptApply> findByStatus(String status) throws SQLException {
        String sql = "select * from adopt_apply where status = ? order by apply_time desc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<AdoptApply> applies = new ArrayList<>();
                while (resultSet.next()) {
                    applies.add(mapApply(resultSet));
                }
                return applies;
            }
        }
    }

    public void updateStatus(Long id, String status, String opinion, String verifyRecord) throws SQLException {
        String sql = "update adopt_apply set status = ?, review_opinion = ?, verify_record = ?, review_time = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setString(2, opinion);
            statement.setString(3, verifyRecord);
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(5, id);
            statement.executeUpdate();
        }
    }

    public void updatePetStatus(Long petId, String status) throws SQLException {
        String sql = "update pets set status = ? where id = ?";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setLong(2, petId);
            statement.executeUpdate();
        }
    }

    public boolean isApplied(Long userId, Long petId) throws SQLException {
        String sql = "select count(*) from adopt_apply where user_id = ? and pet_id = ? and status != '已拒绝'";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, petId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    private AdoptApply mapApply(ResultSet resultSet) throws SQLException {
        AdoptApply apply = new AdoptApply();
        apply.setId(resultSet.getLong("id"));
        apply.setUserId(resultSet.getLong("user_id"));
        apply.setPetId(resultSet.getLong("pet_id"));
        apply.setApplicantName(resultSet.getString("applicant_name"));
        apply.setPhone(resultSet.getString("phone"));
        apply.setAddress(resultSet.getString("address"));
        apply.setExperience(resultSet.getString("experience"));
        apply.setReason(resultSet.getString("reason"));
        apply.setPlan(resultSet.getString("plan"));
        apply.setApplyTime(resultSet.getTimestamp("apply_time").toLocalDateTime());
        apply.setStatus(resultSet.getString("status"));
        apply.setReviewOpinion(resultSet.getString("review_opinion"));
        apply.setVerifyRecord(resultSet.getString("verify_record"));
        apply.setAgreementSigned(resultSet.getString("agreement_signed"));
        if (resultSet.getTimestamp("review_time") != null) {
            apply.setReviewTime(resultSet.getTimestamp("review_time").toLocalDateTime());
        }
        return apply;
    }

    public void loadRelations(AdoptApply apply) throws SQLException {
        UserDao userDao = new UserDao();
        PetDao petDao = new PetDao();
        apply.setUser(userDao.findByUserId(apply.getUserId()));
        apply.setPet(petDao.findById(apply.getPetId()));
    }
}
