package com.campuspet.view.volunteer;

import com.campuspet.entity.FeedPoint;
import com.campuspet.entity.FeedRecord;
import com.campuspet.entity.User;
import com.campuspet.service.FeedPointService;
import com.campuspet.service.FeedRecordService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class FeedRecordListPanel extends JPanel {
    private User user;
    private FeedPointService feedPointService = new FeedPointService();
    private FeedRecordService recordService = new FeedRecordService();
    private List<FeedRecord> records;
    private JTable table;
    private DefaultTableModel tableModel;

    public FeedRecordListPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("投喂记录");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("新增记录");
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"投喂点", "宠物数量", "食物数量", "食物类型", "投喂时间"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(event -> showAddDialog());
        refreshButton.addActionListener(event -> loadRecords());

        loadRecords();
    }

    private void loadRecords() {
        tableModel.setRowCount(0);
        try {
            records = recordService.getMyRecords(user.getId());
            for (FeedRecord record : records) {
                tableModel.addRow(new Object[]{
                        record.getFeedPoint() != null ? record.getFeedPoint().getLocation() : "未知",
                        record.getPetCount(),
                        record.getFoodAmount(),
                        record.getFoodType(),
                        record.getFeedTime()
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载投喂记录失败: " + e.getMessage());
        }
    }

    private void showAddDialog() {
        new AddFeedRecordDialog((JFrame) SwingUtilities.getWindowAncestor(this), user).setVisible(true);
    }
}
