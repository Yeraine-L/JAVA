package com.campuspet.service;

import com.campuspet.dao.GoodsDao;
import com.campuspet.dao.GoodsRecordDao;
import com.campuspet.entity.Goods;
import com.campuspet.entity.GoodsRecord;
import com.campuspet.entity.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class GoodsService {
    private GoodsDao dao = new GoodsDao();
    private GoodsRecordDao recordDao = new GoodsRecordDao();

    public void addGoods(String name, String type, String unit, int minQuantity) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("物资名称不能为空");
            }
            if (type == null || type.trim().isEmpty()) {
                throw new IllegalArgumentException("物资类型不能为空");
            }

            Goods goods = new Goods();
            goods.setName(name);
            goods.setType(type);
            goods.setUnit(unit);
            goods.setTotalQuantity(0);
            goods.setMinQuantity(minQuantity);
            goods.setCreateTime(java.time.LocalDateTime.now());

            dao.save(goods);
        } catch (SQLException e) {
            throw new IllegalStateException("添加物资失败");
        }
    }

    public void inStock(Long goodsId, int quantity, User operator, String remark) {
        try {
            if (goodsId == null) {
                throw new IllegalArgumentException("请选择物资");
            }
            if (quantity <= 0) {
                throw new IllegalArgumentException("入库数量必须 > 0");
            }

            Goods goods = dao.findById(goodsId);
            if (goods == null) {
                throw new IllegalArgumentException("物资不存在");
            }

            int newQuantity = goods.getTotalQuantity() + quantity;
            dao.updateQuantity(goodsId, newQuantity);

            GoodsRecord record = new GoodsRecord();
            record.setGoodsId(goodsId);
            record.setGoodsName(goods.getName());
            record.setType("入库");
            record.setQuantity(quantity);
            record.setOperatorId(operator.getId());
            record.setOperatorName(operator.getUsername());
            record.setRemark(remark);
            record.setCreateTime(java.time.LocalDateTime.now());

            recordDao.save(record);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException("物资入库失败");
        }
    }

    public void outStock(Long goodsId, int quantity, User operator, String remark) {
        try {
            if (goodsId == null) {
                throw new IllegalArgumentException("请选择物资");
            }
            if (quantity <= 0) {
                throw new IllegalArgumentException("出库数量必须 > 0");
            }

            Goods goods = dao.findById(goodsId);
            if (goods == null) {
                throw new IllegalArgumentException("物资不存在");
            }
            if (quantity > goods.getTotalQuantity()) {
                throw new IllegalArgumentException("库存不足");
            }

            int newQuantity = goods.getTotalQuantity() - quantity;
            dao.updateQuantity(goodsId, newQuantity);

            GoodsRecord record = new GoodsRecord();
            record.setGoodsId(goodsId);
            record.setGoodsName(goods.getName());
            record.setType("出库");
            record.setQuantity(quantity);
            record.setOperatorId(operator.getId());
            record.setOperatorName(operator.getUsername());
            record.setRemark(remark);
            record.setCreateTime(java.time.LocalDateTime.now());

            recordDao.save(record);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException("物资出库失败");
        }
    }

    public List<Goods> getAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("获取物资列表失败");
        }
    }

    public List<Goods> getLowStock() {
        try {
            return dao.findLowStock();
        } catch (SQLException e) {
            throw new IllegalStateException("获取低库存物资失败");
        }
    }

    public Goods getDetail(Long id) {
        try {
            return dao.findById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("获取物资详情失败");
        }
    }

    public void update(Long id, String name, String type, String unit, int minQuantity) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("物资名称不能为空");
            }
            if (type == null || type.trim().isEmpty()) {
                throw new IllegalArgumentException("物资类型不能为空");
            }

            Goods goods = dao.findById(id);
            if (goods == null) {
                throw new IllegalArgumentException("物资不存在");
            }

            goods.setName(name);
            goods.setType(type);
            goods.setUnit(unit);
            goods.setMinQuantity(minQuantity);
            dao.update(goods);
        } catch (SQLException e) {
            throw new IllegalStateException("更新物资失败");
        }
    }
}
