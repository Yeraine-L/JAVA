package com.campuspet.view.volunteer;

import com.campuspet.entity.FeedPoint;
import com.campuspet.entity.User;
import com.campuspet.service.FeedPointService;
import com.campuspet.service.FeedRecordService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class AddFeedRecordDialog extends JDialog {
    private User user;
    private FeedPointService feedPointService = new FeedPointService();
    private FeedRecordService recordService = new FeedRecordService();

    public AddFeedRecordDialog(JFrame owner, User user) {
        super(owner, "新增投喂记录", true);
        this.user = user;

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("投喂点:"), gbc);

        gbc.gridx = 1;
        JComboBox<FeedPoint> feedPointCombo = new JComboBox<>();
        feedPointCombo.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        loadFeedPoints(feedPointCombo);
        rootPanel.add(feedPointCombo, gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("宠物数量:"), gbc);

        gbc.gridx = 1;
        JSpinner petCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        petCountSpinner.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(petCountSpinner, gbc);

        gbc.gridy = 2;
        rootPanel.add(new JLabel("食物数量:"), gbc);

        gbc.gridx = 1;
        JSpinner foodAmountSpinner = new JSpinner(new SpinnerNumberModel(0.1, 0.1, 100.0, 0.1));
        foodAmountSpinner.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(foodAmountSpinner, gbc);

        gbc.gridy = 3;
        rootPanel.add(new JLabel("食物类型:"), gbc);

        gbc.gridx = 1;
        JTextField foodTypeField = new JTextField(20);
        foodTypeField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(foodTypeField, gbc);

        gbc.gridy = 4;
        rootPanel.add(new JLabel("投喂时间:"), gbc);

        gbc.gridx = 1;
        JSpinner feedTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor feedTimeEditor = new JSpinner.DateEditor(feedTimeSpinner, "yyyy-MM-dd HH:mm");
        feedTimeSpinner.setEditor(feedTimeEditor);
        feedTimeSpinner.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(feedTimeSpinner, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("添加");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        addButton.addActionListener(event -> handleAdd(feedPointCombo, petCountSpinner, foodAmountSpinner, foodTypeField, feedTimeSpinner));
        cancelButton.addActionListener(event -> dispose());

        setTitle("新增投喂记录 - " + user.getUsername());
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private void loadFeedPoints(JComboBox<FeedPoint> combo) {
        try {
            List<FeedPoint> feedPoints = feedPointService.getMyFeedPoints(user.getId());
            for (FeedPoint fp : feedPoints) {
                combo.addItem(fp);
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载投喂点失败: " + e.getMessage());
        }
    }

    private void handleAdd(JComboBox<FeedPoint> feedPointCombo, JSpinner petCountSpinner, JSpinner foodAmountSpinner, JTextField foodTypeField, JSpinner feedTimeSpinner) {
        FeedPoint feedPoint = (FeedPoint) feedPointCombo.getSelectedItem();
        Integer petCount = (Integer) petCountSpinner.getValue();
        BigDecimal foodAmount = (BigDecimal) foodAmountSpinner.getValue();
        String foodType = foodTypeField.getText().trim();
        java.util.Date feedTimeDate = (java.util.Date) feedTimeSpinner.getValue();
        java.time.LocalDateTime feedTime = feedTimeDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();

        try {
            if (feedPoint == null) {
                throw new IllegalArgumentException("请选择投喂点");
            }
            if (foodType.isEmpty()) {
                throw new IllegalArgumentException("食物类型不能为空");
            }

            recordService.record(user, feedPoint.getId(), petCount, foodAmount, foodType, feedTime);
            MessageUtil.info(this, "投喂记录添加成功");
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.error(this, e.getMessage());
        }
    }
}
