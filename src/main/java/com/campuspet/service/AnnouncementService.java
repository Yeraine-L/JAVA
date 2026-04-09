package com.campuspet.service;

import com.campuspet.dao.AnnouncementDao;
import com.campuspet.entity.Announcement;
import com.campuspet.entity.User;

import java.sql.SQLException;
import java.util.List;

public class AnnouncementService {
    private AnnouncementDao dao = new AnnouncementDao();

    public void publish(User publisher, String title, String content) {
        try {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("标题不能为空");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("内容不能为空");
            }

            Announcement announcement = new Announcement();
            announcement.setPublisherId(publisher.getId());
            announcement.setPublisherName(publisher.getUsername());
            announcement.setTitle(title);
            announcement.setContent(content);
            announcement.setPublishTime(java.time.LocalDateTime.now());

            dao.save(announcement);
        } catch (SQLException e) {
            throw new IllegalStateException("发布公告失败");
        }
    }

    public void update(Long id, String title, String content) {
        try {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("标题不能为空");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("内容不能为空");
            }

            Announcement announcement = dao.findById(id);
            if (announcement == null) {
                throw new IllegalArgumentException("公告不存在");
            }

            announcement.setTitle(title);
            announcement.setContent(content);
            dao.update(announcement);
        } catch (SQLException e) {
            throw new IllegalStateException("更新公告失败");
        }
    }

    public void delete(Long id) {
        try {
            Announcement announcement = dao.findById(id);
            if (announcement == null) {
                throw new IllegalArgumentException("公告不存在");
            }
            dao.delete(id);
        } catch (SQLException e) {
            throw new IllegalStateException("删除公告失败");
        }
    }

    public Announcement getDetail(Long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("获取公告详情失败");
        }
    }

    public List<Announcement> getAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("获取公告列表失败");
        }
    }

    public List<Announcement> getRecent(int limit) {
        try {
            return dao.findRecent(limit);
        } catch (SQLException e) {
            throw new IllegalStateException("获取最新公告失败");
        }
    }
}
