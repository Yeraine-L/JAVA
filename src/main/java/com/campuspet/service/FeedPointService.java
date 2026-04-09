package com.campuspet.service;

import com.campuspet.dao.FeedPointDao;
import com.campuspet.dao.FeedRecordDao;
import com.campuspet.entity.FeedPoint;
import com.campuspet.entity.FeedRecord;
import com.campuspet.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class FeedPointService {
    private FeedPointDao dao = new FeedPointDao();

    public void apply(User volunteer, String location, String area, String description) {
        try {
            if (location == null || location.trim().isEmpty()) {
                throw new IllegalArgumentException("投喂地点不能为空");
            }
            if (area == null || area.trim().isEmpty()) {
                throw new IllegalArgumentException("所在区域不能为空");
            }
            if (description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("说明不能为空");
            }

            FeedPoint feedPoint = new FeedPoint();
            feedPoint.setVolunteerId(volunteer.getId());
            feedPoint.setVolunteerName(volunteer.getUsername());
            feedPoint.setLocation(location);
            feedPoint.setArea(area);
            feedPoint.setDescription(description);
            feedPoint.setApplyTime(java.time.LocalDateTime.now());

            dao.save(feedPoint);
        } catch (SQLException e) {
            throw new IllegalStateException("申请投喂点失败");
        }
    }

    public void review(Long id, String status, String opinion) {
        try {
            FeedPoint feedPoint = dao.findById(id);
            if (feedPoint == null) {
                throw new IllegalArgumentException("投喂点不存在");
            }

            dao.updateStatus(id, status, opinion);
        } catch (SQLException e) {
            throw new IllegalStateException("审核投喂点失败");
        }
    }

    public List<FeedPoint> getAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("获取投喂点列表失败");
        }
    }

    public List<FeedPoint> getMyFeedPoints(Long volunteerId) {
        try {
            return dao.findByVolunteerId(volunteerId);
        } catch (SQLException e) {
            throw new IllegalStateException("获取我的投喂点失败");
        }
    }

    public FeedPoint getDetail(Long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("获取投喂点详情失败");
        }
    }

    public void update(Long id, String location, String area, String description) {
        try {
            if (location == null || location.trim().isEmpty()) {
                throw new IllegalArgumentException("投喂地点不能为空");
            }
            if (area == null || area.trim().isEmpty()) {
                throw new IllegalArgumentException("所在区域不能为空");
            }

            FeedPoint feedPoint = dao.findById(id);
            if (feedPoint == null) {
                throw new IllegalArgumentException("投喂点不存在");
            }

            feedPoint.setLocation(location);
            feedPoint.setArea(area);
            feedPoint.setDescription(description);
            dao.update(feedPoint);
        } catch (SQLException e) {
            throw new IllegalStateException("更新投喂点失败");
        }
    }

    public void delete(Long id) {
        try {
            FeedPoint feedPoint = dao.findById(id);
            if (feedPoint == null) {
                throw new IllegalArgumentException("投喂点不存在");
            }
            dao.delete(id);
        } catch (SQLException e) {
            throw new IllegalStateException("删除投喂点失败");
        }
    }
}
