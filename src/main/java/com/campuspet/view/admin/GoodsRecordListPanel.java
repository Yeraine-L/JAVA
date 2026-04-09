package com.campuspet.view.admin;

import com.campuspet.entity.Goods;
import com.campuspet.entity.GoodsRecord;
import com.campuspet.entity.User;
import com.campuspet.service.GoodsService;
import com.campuspet.service.GoodsRecordService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GoodsRecordListPanel extends JPanel {
    private User user;
    private GoodsService goodsService = new GoodsService();
    private GoodsRecordService recordService = new GoodsRecordService();
    private List<GoodsRecord> records;
    private JTable table;
    private DefaultTableModel tableModel;

    public GoodsRecordListPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("物资出入库记录");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton inButton = new JButton("入库");
        JButton outButton = new JButton("出库");
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(inButton);
        buttonPanel.add(outButton);
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"物资名称", "类型", "数量", "操作人", "操作时间", "备注"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        inButton.addActionListener(event -> showInStockDialog());
        outButton.addActionListener(event -> showOutStockDialog());
        refreshButton.addActionListener(event -> loadRecords());

        loadRecords();
    }

    private void loadRecords() {
        tableModel.setRowCount(0);
        try {
            records = recordService.getAll();
            for (GoodsRecord record : records) {
                tableModel.addRow(new Object[]{
                        record.getGoodsName(),
                        record.getType(),
                        record.getQuantity(),
                        record.getOperatorName(),
                        record.getCreateTime(),
                        record.getRemark()
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载出入库记录失败: " + e.getMessage());
        }
    }

    private void showInStockDialog() {
        new InStockDialog((JFrame) SwingUtilities.getWindowAncestor(this), user).setVisible(true);
    }

    private void showOutStockDialog() {
        new OutStockDialog((JFrame) SwingUtilities.getWindowAncestor(this), user).setVisible(true);
    }
}
