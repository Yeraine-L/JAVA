package com.campuspet.view.volunteer;

import com.campuspet.entity.FeedPoint;
import com.campuspet.entity.User;
import com.campuspet.service.FeedPointService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FeedPointListPanel extends JPanel {
    private User user;
    private FeedPointService service = new FeedPointService();
    private List<FeedPoint> feedPoints;
    private JTable table;
    private DefaultTableModel tableModel;

    public FeedPointListPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("投喂点管理");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("申请投喂点");
        JButton editButton = new JButton("编辑");
        JButton deleteButton = new JButton("删除");
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"地点", "区域", "状态", "申请时间"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(event -> showApplyDialog());
        editButton.addActionListener(event -> showEditDialog());
        deleteButton.addActionListener(event -> deleteFeedPoint());
        refreshButton.addActionListener(event -> loadFeedPoints());

        loadFeedPoints();
    }

    private void loadFeedPoints() {
        tableModel.setRowCount(0);
        try {
            feedPoints = service.getMyFeedPoints(user.getId());
            for (FeedPoint fp : feedPoints) {
                tableModel.addRow(new Object[]{
                        fp.getLocation(),
                        fp.getArea(),
                        fp.getStatus(),
                        fp.getApplyTime()
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载投喂点列表失败: " + e.getMessage());
        }
    }

    private void showApplyDialog() {
        new ApplyFeedPointDialog((JFrame) SwingUtilities.getWindowAncestor(this), user).setVisible(true);
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageUtil.warn(this, "请先选择一条投喂点");
            return;
        }

        FeedPoint feedPoint = feedPoints.get(selectedRow);
        new EditFeedPointDialog((JFrame) SwingUtilities.getWindowAncestor(this), user, feedPoint).setVisible(true);
    }

    private void deleteFeedPoint() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageUtil.warn(this, "请先选择一条投喂点");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除这个投喂点吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.delete(feedPoints.get(selectedRow).getId());
                MessageUtil.info(this, "投喂点删除成功");
                loadFeedPoints();
            } catch (Exception e) {
                MessageUtil.error(this, "删除投喂点失败: " + e.getMessage());
            }
        }
    }
}
