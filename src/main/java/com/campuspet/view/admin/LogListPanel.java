package com.campuspet.view.admin;

import com.campuspet.entity.Log;
import com.campuspet.service.LogService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LogListPanel extends JPanel {
    private LogService service = new LogService();
    private List<Log> logs;
    private JTable table;
    private DefaultTableModel tableModel;

    public LogListPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("系统日志");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"操作时间", "操作人", "操作类型", "操作内容", "IP地址"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshButton.addActionListener(event -> loadLogs());

        loadLogs();
    }

    private void loadLogs() {
        tableModel.setRowCount(0);
        try {
            logs = service.getAll();
            for (Log log : logs) {
                tableModel.addRow(new Object[]{
                        log.getCreateTime(),
                        log.getOperatorName(),
                        log.getOperationType(),
                        log.getOperationContent(),
                        log.getIpAddress()
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载日志列表失败: " + e.getMessage());
        }
    }
}
