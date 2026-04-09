package com.campuspet.view.admin;

import com.campuspet.entity.User;
import com.campuspet.service.UserService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserListPanel extends JPanel {
    private UserService service = new UserService();
    private List<User> users;
    private JTable table;
    private DefaultTableModel tableModel;

    public UserListPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("用户管理");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"用户名", "手机号", "角色", "状态"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshButton.addActionListener(event -> loadUsers());

        loadUsers();
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        try {
            users = service.getAll();
            for (User user : users) {
                tableModel.addRow(new Object[]{
                        user.getUsername(),
                        user.getPhone(),
                        user.getRole().getDisplayName(),
                        user.getStatus()
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载用户列表失败: " + e.getMessage());
        }
    }
}
