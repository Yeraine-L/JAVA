package com.campuspet.service;

import com.campuspet.dao.LogDao;
import com.campuspet.entity.Log;

import java.sql.SQLException;
import java.util.List;

public class LogService {
    private LogDao dao = new LogDao();

    public void save(Log log) {
        try {
            dao.save(log);
        } catch (SQLException e) {
            throw new IllegalStateException("保存日志失败");
        }
    }

    public List<Log> getAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("获取日志列表失败");
        }
    }
}
