package com.campuspet.view.volunteer;

import com.campuspet.entity.User;
import com.campuspet.service.ExceptionReportService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class ReportExceptionDialog extends JDialog {
    private User user;
    private ExceptionReportService service = new ExceptionReportService();

    public ReportExceptionDialog(JFrame owner, User user) {
        super(owner, "上报异常", true);
        this.user = user;

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("地点:"), gbc);

        gbc.gridx = 1;
        JTextField locationField = new JTextField(30);
        locationField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(locationField, gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("区域:"), gbc);

        gbc.gridx = 1;
        JTextField areaField = new JTextField(30);
        areaField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(areaField, gbc);

        gbc.gridy = 2;
        rootPanel.add(new JLabel("类型:"), gbc);

        gbc.gridx = 1;
        String[] types = {"宠物受伤", "宠物生病", "食物不足", "设施损坏", "其他"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        typeCombo.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(typeCombo, gbc);

        gbc.gridy = 3;
        rootPanel.add(new JLabel("描述:"), gbc);

        gbc.gridx = 1;
        JTextArea descriptionArea = new JTextArea(5, 30);
        descriptionArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        rootPanel.add(descriptionScroll, gbc);

        gbc.gridy = 4;
        rootPanel.add(new JLabel("照片:"), gbc);

        gbc.gridx = 1;
        JTextField photoField = new JTextField(30);
        photoField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        photoField.setText("待上传");
        photoField.setEditable(false);
        rootPanel.add(photoField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton uploadButton = new JButton("上传照片");
        JButton reportButton = new JButton("上报");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(uploadButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        uploadButton.addActionListener(event -> uploadPhoto(photoField));
        reportButton.addActionListener(event -> handleReport(locationField, areaField, typeCombo, descriptionArea));
        cancelButton.addActionListener(event -> dispose());

        setTitle("上报异常 - " + user.getUsername());
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private void uploadPhoto(JTextField photoField) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择照片");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            photoField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void handleReport(JTextField locationField, JTextField areaField, JComboBox<String> typeCombo, JTextArea descriptionArea) {
        String location = locationField.getText().trim();
        String area = areaField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();
        String description = descriptionArea.getText().trim();
        String photo = "";

        try {
            if (location.isEmpty()) {
                throw new IllegalArgumentException("地点不能为空");
            }
            if (area.isEmpty()) {
                throw new IllegalArgumentException("区域不能为空");
            }
            if (description.isEmpty()) {
                throw new IllegalArgumentException("描述不能为空");
            }

            service.report(user, location, area, type, description, photo);
            MessageUtil.info(this, "异常上报成功");
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.error(this, e.getMessage());
        }
    }
}
