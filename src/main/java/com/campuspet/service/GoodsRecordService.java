package com.campuspet.service;

import com.campuspet.dao.GoodsRecordDao;
import com.campuspet.entity.GoodsRecord;

import java.sql.SQLException;
import java.util.List;

public class GoodsRecordService {
    private GoodsRecordDao dao = new GoodsRecordDao();

    public List<GoodsRecord> getAllRecords() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("获取所有出入库记录失败");
        }
    }

    public List<GoodsRecord> getGoodsRecords(Long goodsId) {
        try {
            return dao.findByGoodsId(goodsId);
        } catch (SQLException e) {
            throw new IllegalStateException("获取物资出入库记录失败");
        }
    }

    public GoodsRecord getDetail(Long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("获取出入库记录详情失败");
        }
    }

    public List<GoodsRecord> getAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("获取所有出入库记录失败");
        }
    }
}
