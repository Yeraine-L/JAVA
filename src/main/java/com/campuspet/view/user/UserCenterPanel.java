package com.campuspet.view.user;

import com.campuspet.entity.User;
import com.campuspet.service.UserService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class UserCenterPanel extends JPanel {
    private User user;
    private UserService userService = new UserService();

    public UserCenterPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel avatarLabel = new JLabel("👤");
        avatarLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
        avatarLabel.setPreferredSize(new Dimension(80, 80));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));

        JLabel nameLabel = new JLabel("用户名: " + user.getUsername());
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));

        JLabel roleLabel = new JLabel("角色: " + user.getRole().getDisplayName() + " | 用户ID: " + user.getId());
        roleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        infoPanel.add(nameLabel);
        infoPanel.add(roleLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(avatarLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(infoPanel, gbc);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        panel.add(createCardButton("修改密码", "修改您的登录密码", "🔑"));
        panel.add(createCardButton("我的申请", "查看我的领养申请记录", "📋"));
        panel.add(createCardButton("消息中心", "查看系统消息通知", "🔔"));
        panel.add(createCardButton("领养回访", "查看领养宠物的回访记录", "🐾"));

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("退出登录");
        logoutButton.addActionListener(event -> {
            int confirm = JOptionPane.showConfirmDialog(this, "确定要退出登录吗？", "确认退出", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "退出登录功能待实现", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(logoutButton);
        return panel;
    }

    private JPanel createCardButton(String title, String description, String icon) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("微软雅黑", Font.BOLD, 32));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        descLabel.setForeground(new Color(100, 100, 100));

        leftPanel.add(titleLabel);
        leftPanel.add(descLabel);

        card.add(iconLabel, BorderLayout.WEST);
        card.add(leftPanel, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleCardClick(title);
            }
        });

        return card;
    }

    private void handleCardClick(String title) {
        switch (title) {
            case "修改密码":
                new ChangePasswordDialog((JFrame) SwingUtilities.getWindowAncestor(this), user).setVisible(true);
                break;
            case "我的申请":
                MessageUtil.info(this, "我的申请列表功能待实现");
                break;
            case "消息中心":
                MessageUtil.info(this, "消息中心功能待实现");
                break;
            case "领养回访":
                MessageUtil.info(this, "领养回访功能待实现");
                break;
        }
    }
}
