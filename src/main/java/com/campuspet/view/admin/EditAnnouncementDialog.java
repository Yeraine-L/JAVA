package com.campuspet.view.admin;

import com.campuspet.entity.Announcement;
import com.campuspet.entity.User;
import com.campuspet.service.AnnouncementService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class EditAnnouncementDialog extends JDialog {
    private User user;
    private AnnouncementService service = new AnnouncementService();
    private Announcement announcement;

    public EditAnnouncementDialog(JFrame owner, User user, Announcement announcement) {
        super(owner, "编辑公告", true);
        this.user = user;
        this.announcement = announcement;

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("标题:"), gbc);

        gbc.gridx = 1;
        JTextField titleField = new JTextField(30);
        titleField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        titleField.setText(announcement.getTitle());
        rootPanel.add(titleField, gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("内容:"), gbc);

        gbc.gridx = 1;
        JTextArea contentArea = new JTextArea(8, 30);
        contentArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setText(announcement.getContent());
        JScrollPane contentScroll = new JScrollPane(contentArea);
        rootPanel.add(contentScroll, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        saveButton.addActionListener(event -> handleSave(titleField, contentArea));
        cancelButton.addActionListener(event -> dispose());

        setTitle("编辑公告 - " + announcement.getTitle());
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private void handleSave(JTextField titleField, JTextArea contentArea) {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        try {
            service.update(announcement.getId(), title, content);
            MessageUtil.info(this, "公告更新成功");
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.error(this, e.getMessage());
        }
    }
}
