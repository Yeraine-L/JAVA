package com.campuspet.service;

import com.campuspet.dao.ExceptionReportDao;
import com.campuspet.entity.ExceptionReport;
import com.campuspet.entity.User;

import java.sql.SQLException;
import java.util.List;

public class ExceptionReportService {
    private ExceptionReportDao dao = new ExceptionReportDao();

    public void report(User volunteer, String location, String area, String type, String description, String photo) {
        try {
            if (location == null || location.trim().isEmpty()) {
                throw new IllegalArgumentException("异常地点不能为空");
            }
            if (type == null || type.trim().isEmpty()) {
                throw new IllegalArgumentException("异常类型不能为空");
            }
            if (description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("异常描述不能为空");
            }

            ExceptionReport report = new ExceptionReport();
            report.setVolunteerId(volunteer.getId());
            report.setVolunteerName(volunteer.getUsername());
            report.setLocation(location);
            report.setArea(area);
            report.setType(type);
            report.setDescription(description);
            report.setPhoto(photo);
            report.setCreateTime(java.time.LocalDateTime.now());

            dao.save(report);
        } catch (SQLException e) {
            throw new IllegalStateException("上报异常失败");
        }
    }

    public void handle(Long id, String status, String opinion) {
        try {
            ExceptionReport report = dao.findById(id);
            if (report == null) {
                throw new IllegalArgumentException("异常不存在");
            }

            dao.updateStatus(id, status, opinion);
        } catch (SQLException e) {
            throw new IllegalStateException("处理异常失败");
        }
    }

    public List<ExceptionReport> getAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("获取异常列表失败");
        }
    }

    public List<ExceptionReport> getMyReports(Long volunteerId) {
        try {
            return dao.findByVolunteerId(volunteerId);
        } catch (SQLException e) {
            throw new IllegalStateException("获取我的异常失败");
        }
    }

    public ExceptionReport getDetail(Long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("获取异常详情失败");
        }
    }
}
