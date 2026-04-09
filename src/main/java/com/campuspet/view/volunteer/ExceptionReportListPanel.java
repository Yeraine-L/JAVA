package com.campuspet.view.volunteer;

import com.campuspet.entity.ExceptionReport;
import com.campuspet.entity.User;
import com.campuspet.service.ExceptionReportService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExceptionReportListPanel extends JPanel {
    private User user;
    private ExceptionReportService service = new ExceptionReportService();
    private List<ExceptionReport> reports;
    private JTable table;
    private DefaultTableModel tableModel;

    public ExceptionReportListPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("异常上报");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("上报异常");
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"地点", "区域", "类型", "状态", "上报时间"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(event -> showReportDialog());
        refreshButton.addActionListener(event -> loadReports());

        loadReports();
    }

    private void loadReports() {
        tableModel.setRowCount(0);
        try {
            reports = service.getMyReports(user.getId());
            for (ExceptionReport report : reports) {
                tableModel.addRow(new Object[]{
                        report.getLocation(),
                        report.getArea(),
                        report.getType(),
                        report.getStatus(),
                        report.getCreateTime()
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载异常列表失败: " + e.getMessage());
        }
    }

    private void showReportDialog() {
        new ReportExceptionDialog((JFrame) SwingUtilities.getWindowAncestor(this), user).setVisible(true);
    }
}
