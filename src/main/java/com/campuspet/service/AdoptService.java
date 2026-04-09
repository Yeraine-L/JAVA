package com.campuspet.service;

import com.campuspet.dao.AdoptApplyDao;
import com.campuspet.dao.PetDao;
import com.campuspet.dao.UserDao;
import com.campuspet.entity.AdoptApply;
import com.campuspet.entity.Pet;
import com.campuspet.entity.User;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AdoptService {
    private final AdoptApplyDao applyDao = new AdoptApplyDao();
    private final PetDao petDao = new PetDao();
    private final UserDao userDao = new UserDao();

    public void apply(User user, Pet pet, String name, String phone, String address, String experience, String reason, String plan) {
        if (user == null || pet == null) {
            throw new IllegalArgumentException("用户或宠物信息为空");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("请输入姓名");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("请输入手机号");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("请输入居住地址");
        }
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("请输入领养原因");
        }
        if (plan == null || plan.trim().isEmpty()) {
            throw new IllegalArgumentException("请输入饲养计划");
        }

        try {
            if (applyDao.isApplied(user.getId(), pet.getId())) {
                throw new IllegalArgumentException("您已经申请过这只宠物，请等待审核结果");
            }

            AdoptApply apply = new AdoptApply();
            apply.setUserId(user.getId());
            apply.setPetId(pet.getId());
            apply.setApplicantName(name);
            apply.setPhone(phone);
            apply.setAddress(address);
            apply.setExperience(experience);
            apply.setReason(reason);
            apply.setPlan(plan);
            apply.setApplyTime(LocalDateTime.now());
            apply.setStatus("待审核");

            applyDao.save(apply);

            MessageUtil.info(null, "申请提交成功，请等待管理员审核");
        } catch (SQLException e) {
            throw new IllegalStateException("数据操作失败，请重试");
        }
    }

    public List<AdoptApply> findMyApplies(User user) {
        try {
            return applyDao.findByUserId(user.getId());
        } catch (SQLException e) {
            throw new IllegalStateException("数据加载失败，请检查数据库连接");
        }
    }

    public List<AdoptApply> findPendingApplies() {
        try {
            return applyDao.findByStatus("待审核");
        } catch (SQLException e) {
            throw new IllegalStateException("数据加载失败，请检查数据库连接");
        }
    }

    public void approve(Long applyId, String opinion, String verifyRecord) {
        try {
            AdoptApply apply = applyDao.findById(applyId);
            if (apply == null) {
                throw new IllegalArgumentException("申请不存在");
            }

            applyDao.updateStatus(applyId, "已通过", opinion, verifyRecord);
            applyDao.updatePetStatus(apply.getPetId(), "已领养");

            MessageUtil.info(null, "审核通过");
        } catch (SQLException e) {
            throw new IllegalStateException("审核失败，请重试");
        }
    }

    public void reject(Long applyId, String opinion, String verifyRecord) {
        try {
            AdoptApply apply = applyDao.findById(applyId);
            if (apply == null) {
                throw new IllegalArgumentException("申请不存在");
            }

            applyDao.updateStatus(applyId, "已拒绝", opinion, verifyRecord);

            MessageUtil.info(null, "审核已拒绝");
        } catch (SQLException e) {
            throw new IllegalStateException("审核失败，请重试");
        }
    }

    public List<AdoptApply> findAll() {
        try {
            List<AdoptApply> applies = applyDao.findAll();
            for (AdoptApply apply : applies) {
                applyDao.loadRelations(apply);
            }
            return applies;
        } catch (SQLException e) {
            throw new IllegalStateException("数据加载失败，请检查数据库连接");
        }
    }

    public AdoptApply findById(Long id) {
        try {
            return applyDao.findById(id);
        } catch (SQLException e) {
            throw new IllegalStateException("数据加载失败，请检查数据库连接");
        }
    }
}
