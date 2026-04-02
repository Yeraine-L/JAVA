package com.campuspet.service;

import com.campuspet.dao.UserDao;
import com.campuspet.entity.User;
import com.campuspet.enums.RoleType;
import com.campuspet.utils.ValidationUtil;

import java.sql.SQLException;

public class AuthService {
    private final UserDao userDao = new UserDao();

    public User login(String username, String password, String roleName) {
        if (ValidationUtil.isBlank(username) || ValidationUtil.isBlank(password)) {
            throw new IllegalArgumentException("请输入用户名/密码");
        }

        try {
            RoleType roleType = RoleType.fromDisplayName(roleName);
            User user = userDao.login(username.trim(), password, roleType);
            if (user == null) {
                throw new IllegalArgumentException("账号或密码错误，请重新输入");
            }
            return user;
        } catch (SQLException e) {
            throw new IllegalStateException("数据库连接失败，请先导入脚本并检查数据库配置");
        }
    }

    public void register(String username, String password, String phone, String roleName) {
        if (ValidationUtil.isBlank(username)) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (!ValidationUtil.isValidPassword(password)) {
            throw new IllegalArgumentException("密码不能为空，且长度需≥6位");
        }
        if (!ValidationUtil.isValidPhone(phone)) {
            throw new IllegalArgumentException("请输入正确的手机号");
        }

        RoleType roleType = RoleType.fromDisplayName(roleName);
        if (RoleType.ADMIN.equals(roleType)) {
            throw new IllegalArgumentException("管理员账号无需注册");
        }

        try {
            User existsUser = userDao.findByUsername(username.trim());
            if (existsUser != null) {
                throw new IllegalArgumentException("该用户名已被注册，请更换");
            }

            User user = new User();
            user.setUsername(username.trim());
            user.setPassword(password);
            user.setPhone(phone.trim());
            user.setRole(roleType);
            user.setStatus("正常");
            userDao.save(user);
        } catch (SQLException e) {
            throw new IllegalStateException("数据操作失败，请重试");
        }
    }
}
