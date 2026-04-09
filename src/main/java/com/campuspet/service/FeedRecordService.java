package com.campuspet.service;

import com.campuspet.dao.FeedPointDao;
import com.campuspet.dao.FeedRecordDao;
import com.campuspet.entity.FeedPoint;
import com.campuspet.entity.FeedRecord;
import com.campuspet.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class FeedRecordService {
    private FeedPointDao feedPointDao = new FeedPointDao();
    private FeedRecordDao dao = new FeedRecordDao();

    public void record(User volunteer, Long feedPointId, Integer petCount, BigDecimal foodAmount, String foodType, java.time.LocalDateTime feedTime) {
        try {
            if (feedPointId == null) {
                throw new IllegalArgumentException("投喂点不能为空");
            }
            if (petCount == null || petCount < 1) {
                throw new IllegalArgumentException("宠物数量必须 ≥ 1");
            }
            if (foodAmount == null || foodAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("食物数量必须 > 0");
            }
            if (feedTime.isAfter(java.time.LocalDateTime.now())) {
                throw new IllegalArgumentException("投喂时间不能早于当前时间");
            }

            FeedPoint feedPoint = feedPointDao.findById(feedPointId);
            if (feedPoint == null) {
                throw new IllegalArgumentException("投喂点不存在");
            }

            FeedRecord record = new FeedRecord();
            record.setFeedPointId(feedPointId);
            record.setVolunteerId(volunteer.getId());
            record.setVolunteerName(volunteer.getUsername());
            record.setPetCount(petCount);
            record.setFoodAmount(foodAmount);
            record.setFoodType(foodType);
            record.setFeedTime(feedTime);
            record.setCreateTime(java.time.LocalDateTime.now());

            dao.save(record);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException("录入投喂记录失败");
        }
    }

    public List<FeedRecord> getMyRecords(Long volunteerId) {
        try {
            return dao.findByVolunteerId(volunteerId);
        } catch (SQLException e) {
            throw new IllegalStateException("获取投喂记录失败");
        }
    }

    public List<FeedRecord> getAll() {
        try {
            return dao.findRecent(100);
        } catch (SQLException e) {
            throw new IllegalStateException("获取所有投喂记录失败");
        }
    }

    public FeedRecord getDetail(Long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("获取投喂记录详情失败");
        }
    }
}
