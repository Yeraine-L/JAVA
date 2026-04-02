package com.campuspet.view.login;

import com.campuspet.config.AppConfig;
import com.campuspet.service.AuthService;
import com.campuspet.utils.MessageUtil;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class RegisterDialog extends JDialog {
    private final JTextField usernameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private final JTextField phoneField = new JTextField(18);
    private final JComboBox<String> roleBox = new JComboBox<>(new String[]{"普通用户", "志愿者"});
    private final AuthService authService = new AuthService();

    public RegisterDialog(Frame owner) {
        super(owner, AppConfig.APP_NAME + " - 注册", true);
        setSize(460, 330);
        setLocationRelativeTo(owner);

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("用户名："), gbc);
        gbc.gridx = 1;
        rootPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        rootPanel.add(new JLabel("密码："), gbc);
        gbc.gridx = 1;
        rootPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rootPanel.add(new JLabel("手机号："), gbc);
        gbc.gridx = 1;
        rootPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        rootPanel.add(new JLabel("身份类型："), gbc);
        gbc.gridx = 1;
        rootPanel.add(roleBox, gbc);

        JButton confirmButton = new JButton("确认注册");
        JButton cancelButton = new JButton("取消");

        gbc.gridx = 0;
        gbc.gridy = 4;
        rootPanel.add(confirmButton, gbc);
        gbc.gridx = 1;
        rootPanel.add(cancelButton, gbc);

        confirmButton.addActionListener(event -> handleRegister());
        cancelButton.addActionListener(event -> dispose());

        setContentPane(rootPanel);
    }

    private void handleRegister() {
        try {
            authService.register(
                    usernameField.getText(),
                    new String(passwordField.getPassword()),
                    phoneField.getText(),
                    String.valueOf(roleBox.getSelectedItem())
            );
            MessageUtil.info(this, "注册成功，请返回登录");
            dispose();
        } catch (IllegalArgumentException | IllegalStateException e) {
            MessageUtil.warn(this, e.getMessage());
        }
    }
}
