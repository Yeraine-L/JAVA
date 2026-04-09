package com.campuspet.view.admin;

import com.campuspet.entity.User;
import com.campuspet.service.AnnouncementService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class PublishAnnouncementDialog extends JDialog {
    private User user;
    private AnnouncementService service = new AnnouncementService();

    public PublishAnnouncementDialog(JFrame owner, User user) {
        super(owner, "发布公告", true);
        this.user = user;

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
        rootPanel.add(titleField, gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("内容:"), gbc);

        gbc.gridx = 1;
        JTextArea contentArea = new JTextArea(8, 30);
        contentArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        JScrollPane contentScroll = new JScrollPane(contentArea);
        rootPanel.add(contentScroll, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton publishButton = new JButton("发布");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(publishButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        publishButton.addActionListener(event -> handlePublish(titleField, contentArea));
        cancelButton.addActionListener(event -> dispose());

        setTitle("发布公告 - " + user.getUsername());
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private void handlePublish(JTextField titleField, JTextArea contentArea) {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        try {
            service.publish(user, title, content);
            MessageUtil.info(this, "公告发布成功");
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.error(this, e.getMessage());
        }
    }
}
