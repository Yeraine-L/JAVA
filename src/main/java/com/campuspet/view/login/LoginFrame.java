package com.campuspet.view.login;

import com.campuspet.config.AppConfig;
import com.campuspet.entity.User;
import com.campuspet.service.AuthService;
import com.campuspet.utils.MessageUtil;
import com.campuspet.utils.ValidationUtil;
import com.campuspet.view.main.MainFrame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class LoginFrame extends JFrame {
    private final JTextField usernameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private final JComboBox<String> roleBox = new JComboBox<>(new String[]{"普通用户", "志愿者", "管理员"});
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle(AppConfig.APP_NAME + " - 登录");
        setSize(500, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel(AppConfig.APP_NAME);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rootPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        rootPanel.add(new JLabel("用户名："), gbc);
        gbc.gridx = 1;
        rootPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rootPanel.add(new JLabel("密码："), gbc);
        gbc.gridx = 1;
        rootPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        rootPanel.add(new JLabel("角色："), gbc);
        gbc.gridx = 1;
        rootPanel.add(roleBox, gbc);

        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");

        gbc.gridx = 0;
        gbc.gridy = 4;
        rootPanel.add(loginButton, gbc);
        gbc.gridx = 1;
        rootPanel.add(registerButton, gbc);

        loginButton.addActionListener(event -> handleLogin());
        registerButton.addActionListener(event -> new RegisterDialog(this).setVisible(true));

        setContentPane(rootPanel);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (ValidationUtil.isBlank(username) || ValidationUtil.isBlank(password)) {
            MessageUtil.warn(this, "请输入用户名/密码");
            return;
        }

        try {
            User user = authService.login(username, password, String.valueOf(roleBox.getSelectedItem()));
            MainFrame mainFrame = new MainFrame(user);
            mainFrame.setVisible(true);
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.error(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.warn(this, e.getMessage());
            return;
        }
    }
}
