package com.campuspet.service;

import com.campuspet.dao.AdoptApplyDao;
import com.campuspet.dao.UserDao;
import com.campuspet.entity.AdoptApply;
import com.campuspet.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();
    private AdoptApplyDao applyDao = new AdoptApplyDao();

    public User getUserInfo(Long userId) {
        try {
            return userDao.findByUserId(userId);
        } catch (SQLException e) {
            throw new IllegalStateException("获取用户信息失败");
        }
    }

    public void updatePhone(Long userId, String phone) {
        try {
            if (phone == null || phone.trim().isEmpty()) {
                throw new IllegalArgumentException("手机号不能为空");
            }
            userDao.updatePhone(userId, phone);
        } catch (SQLException e) {
            throw new IllegalStateException("更新手机号失败");
        }
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        try {
            if (oldPassword == null || oldPassword.trim().isEmpty()) {
                throw new IllegalArgumentException("旧密码不能为空");
            }
            if (newPassword == null || newPassword.trim().isEmpty()) {
                throw new IllegalArgumentException("新密码不能为空");
            }
            if (newPassword.length() < 6) {
                throw new IllegalArgumentException("新密码长度至少6位");
            }

            User user = userDao.findByUserId(userId);
            if (user == null) {
                throw new IllegalArgumentException("用户不存在");
            }
            if (!user.getPassword().equals(oldPassword)) {
                throw new IllegalArgumentException("旧密码错误");
            }

            userDao.updatePassword(userId, newPassword);
        } catch (SQLException e) {
            throw new IllegalStateException("更新密码失败");
        }
    }

    public List<AdoptApply> getMyApplyList(Long userId) {
        try {
            List<AdoptApply> applies = applyDao.findByUserId(userId);
            for (AdoptApply apply : applies) {
                applyDao.loadRelations(apply);
            }
            return applies;
        } catch (SQLException e) {
            throw new IllegalStateException("获取申请列表失败");
        }
    }

    public List<User> getAll() {
        try {
            return userDao.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("获取用户列表失败");
        }
    }
}
