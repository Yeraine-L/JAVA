package com.campuspet.view.user;

import com.campuspet.entity.AdoptApply;
import com.campuspet.entity.User;
import com.campuspet.service.UserService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyApplyListPanel extends JPanel {
    private User user;
    private UserService userService = new UserService();
    private List<AdoptApply> applies;
    private JTable table;
    private DefaultTableModel tableModel;

    public MyApplyListPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("我的领养申请");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"申请时间", "宠物名称", "申请状态", "审核意见"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshButton.addActionListener(event -> loadApplies());

        loadApplies();
    }

    private void loadApplies() {
        tableModel.setRowCount(0);
        try {
            applies = userService.getMyApplyList(user.getId());
            for (AdoptApply apply : applies) {
                tableModel.addRow(new Object[]{
                        apply.getApplyTime(),
                        apply.getPet() != null ? apply.getPet().getName() : "未知",
                        apply.getStatus(),
                        apply.getReviewOpinion() != null ? apply.getReviewOpinion() : "无"
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载申请列表失败: " + e.getMessage());
        }
    }
}
