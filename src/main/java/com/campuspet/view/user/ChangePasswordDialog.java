package com.campuspet.view.user;

import com.campuspet.entity.User;
import com.campuspet.service.UserService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordDialog extends JDialog {
    private User user;
    private UserService userService = new UserService();

    public ChangePasswordDialog(JFrame owner, User user) {
        super(owner, "修改密码", true);
        this.user = user;

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("旧密码:"), gbc);

        gbc.gridx = 1;
        rootPanel.add(createPasswordField(), gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("新密码:"), gbc);

        gbc.gridx = 1;
        rootPanel.add(createPasswordField(), gbc);

        gbc.gridy = 2;
        rootPanel.add(new JLabel("确认密码:"), gbc);

        gbc.gridx = 1;
        rootPanel.add(createPasswordField(), gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        okButton.addActionListener(event -> handleSubmit());
        cancelButton.addActionListener(event -> dispose());

        setTitle("修改密码 - " + user.getUsername());
        setSize(400, 280);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        return field;
    }

    private void handleSubmit() {
        JPasswordField[] fields = new JPasswordField[3];
        Component[] components = ((JPanel) getContentPane()).getComponents();
        int index = 0;
        for (Component comp : components) {
            if (comp instanceof JPasswordField) {
                fields[index++] = (JPasswordField) comp;
            }
        }

        String oldPassword = new String(fields[0].getPassword());
        String newPassword = new String(fields[1].getPassword());
        String confirmPassword = new String(fields[2].getPassword());

        try {
            if (oldPassword.isEmpty()) {
                throw new IllegalArgumentException("旧密码不能为空");
            }
            if (newPassword.isEmpty()) {
                throw new IllegalArgumentException("新密码不能为空");
            }
            if (newPassword.length() < 6) {
                throw new IllegalArgumentException("新密码长度至少6位");
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("两次密码不一致");
            }

            userService.updatePassword(user.getId(), oldPassword, newPassword);
            MessageUtil.info(this, "密码修改成功");
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.error(this, e.getMessage());
        }
    }
}
