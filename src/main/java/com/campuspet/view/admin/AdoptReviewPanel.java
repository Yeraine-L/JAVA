package com.campuspet.view.admin;

import com.campuspet.entity.AdoptApply;
import com.campuspet.entity.User;
import com.campuspet.service.AdoptService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdoptReviewPanel extends JPanel {
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"编号", "申请人", "宠物名称", "申请时间", "状态"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);
    private final AdoptService adoptService = new AdoptService();
    private final List<AdoptApply> currentApplies = new java.util.ArrayList<>();

    public AdoptReviewPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("领养申请审核");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("刷新");
        JButton reviewButton = new JButton("审核");
        buttonPanel.add(refreshButton);
        buttonPanel.add(reviewButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshButton.addActionListener(event -> loadApplies());
        reviewButton.addActionListener(event -> handleReview());

        loadApplies();
    }

    private void loadApplies() {
        try {
            currentApplies.clear();
            currentApplies.addAll(adoptService.findAll());
            refreshTable();
        } catch (IllegalStateException e) {
            MessageUtil.warn(this, e.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (AdoptApply apply : currentApplies) {
            tableModel.addRow(new Object[]{
                    apply.getId(),
                    apply.getApplicantName(),
                    apply.getPet() != null ? apply.getPet().getName() : "未知",
                    apply.getApplyTime(),
                    apply.getStatus()
            });
        }
    }

    private void handleReview() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageUtil.warn(this, "请先选择一条申请记录");
            return;
        }

        AdoptApply apply = currentApplies.get(selectedRow);
        if (!"待审核".equals(apply.getStatus())) {
            MessageUtil.warn(this, "该申请已审核，无法再次审核");
            return;
        }

        showReviewDialog(apply);
    }

    private void showReviewDialog(AdoptApply apply) {
        JDialog dialog = new JDialog((Frame) null, "审核申请 - " + apply.getApplicantName(), true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("申请人：" + apply.getApplicantName()), gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("手机号：" + apply.getPhone()), gbc);

        gbc.gridy = 2;
        rootPanel.add(new JLabel("宠物：" + (apply.getPet() != null ? apply.getPet().getName() : "未知")), gbc);

        gbc.gridy = 3;
        rootPanel.add(new JLabel("领养原因："), gbc);

        gbc.gridy = 4;
        JTextArea reasonArea = new JTextArea(3, 30);
        reasonArea.setText(apply.getReason());
        reasonArea.setEditable(false);
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        rootPanel.add(new JScrollPane(reasonArea), gbc);

        gbc.gridy = 5;
        rootPanel.add(new JLabel("审核意见："), gbc);

        gbc.gridy = 6;
        JTextArea opinionArea = new JTextArea(3, 30);
        rootPanel.add(new JScrollPane(opinionArea), gbc);

        gbc.gridy = 7;
        rootPanel.add(new JLabel("电话核实："), gbc);

        gbc.gridy = 8;
        JTextArea verifyArea = new JTextArea(2, 30);
        rootPanel.add(new JScrollPane(verifyArea), gbc);

        JPanel buttonPanel = new JPanel();
        JButton approveButton = new JButton("通过");
        JButton rejectButton = new JButton("拒绝");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 9;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        approveButton.addActionListener(e -> {
            String opinion = opinionArea.getText();
            String verify = verifyArea.getText();
            if (opinion.trim().isEmpty()) {
                MessageUtil.warn(dialog, "请输入审核意见");
                return;
            }
            adoptService.approve(apply.getId(), opinion, verify);
            loadApplies();
            dialog.dispose();
        });

        rejectButton.addActionListener(e -> {
            String opinion = opinionArea.getText();
            String verify = verifyArea.getText();
            if (opinion.trim().isEmpty()) {
                MessageUtil.warn(dialog, "请输入审核意见");
                return;
            }
            adoptService.reject(apply.getId(), opinion, verify);
            loadApplies();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setContentPane(rootPanel);
        dialog.setVisible(true);
    }
}
