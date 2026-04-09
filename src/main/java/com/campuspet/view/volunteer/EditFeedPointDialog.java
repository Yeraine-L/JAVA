package com.campuspet.view.volunteer;

import com.campuspet.entity.FeedPoint;
import com.campuspet.entity.User;
import com.campuspet.service.FeedPointService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class EditFeedPointDialog extends JDialog {
    private User user;
    private FeedPointService service = new FeedPointService();
    private FeedPoint feedPoint;

    public EditFeedPointDialog(JFrame owner, User user, FeedPoint feedPoint) {
        super(owner, "编辑投喂点", true);
        this.user = user;
        this.feedPoint = feedPoint;

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
        locationField.setText(feedPoint.getLocation());
        rootPanel.add(locationField, gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("区域:"), gbc);

        gbc.gridx = 1;
        JTextField areaField = new JTextField(30);
        areaField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        areaField.setText(feedPoint.getArea());
        rootPanel.add(areaField, gbc);

        gbc.gridy = 2;
        rootPanel.add(new JLabel("说明:"), gbc);

        gbc.gridx = 1;
        JTextArea descriptionArea = new JTextArea(5, 30);
        descriptionArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setText(feedPoint.getDescription());
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        rootPanel.add(descriptionScroll, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        saveButton.addActionListener(event -> handleSave(locationField, areaField, descriptionArea));
        cancelButton.addActionListener(event -> dispose());

        setTitle("编辑投喂点 - " + feedPoint.getLocation());
        setSize(500, 350);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private void handleSave(JTextField locationField, JTextField areaField, JTextArea descriptionArea) {
        String location = locationField.getText().trim();
        String area = areaField.getText().trim();
        String description = descriptionArea.getText().trim();

        try {
            service.update(feedPoint.getId(), location, area, description);
            MessageUtil.info(this, "投喂点更新成功");
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.error(this, e.getMessage());
        }
    }
}
