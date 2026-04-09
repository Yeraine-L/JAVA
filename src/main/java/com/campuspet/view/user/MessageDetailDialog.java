package com.campuspet.view.user;

import com.campuspet.entity.Message;

import javax.swing.*;
import java.awt.*;

public class MessageDetailDialog extends JDialog {
    private Message message;

    public MessageDetailDialog(JFrame owner, Message message) {
        super(owner, "消息详情", true);
        this.message = message;

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("标题:"), gbc);

        gbc.gridx = 1;
        JTextField titleField = new JTextField(message.getTitle());
        titleField.setEditable(false);
        titleField.setFont(new Font("微软雅黑", Font.BOLD, 14));
        rootPanel.add(titleField, gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("内容:"), gbc);

        gbc.gridx = 1;
        JTextArea contentArea = new JTextArea(message.getContent());
        contentArea.setEditable(false);
        contentArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        JScrollPane contentScroll = new JScrollPane(contentArea);
        rootPanel.add(contentScroll, gbc);

        gbc.gridy = 2;
        rootPanel.add(new JLabel("类型:"), gbc);

        gbc.gridx = 1;
        JTextField typeField = new JTextField(message.getType());
        typeField.setEditable(false);
        rootPanel.add(typeField, gbc);

        gbc.gridy = 3;
        rootPanel.add(new JLabel("发送时间:"), gbc);

        gbc.gridx = 1;
        JTextField timeField = new JTextField(message.getSendTime().toString());
        timeField.setEditable(false);
        rootPanel.add(timeField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton markReadButton = new JButton("标记已读");
        JButton closeButton = new JButton("关闭");
        buttonPanel.add(markReadButton);
        buttonPanel.add(closeButton);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        markReadButton.addActionListener(event -> {
            // TODO: 调用服务层标记已读
            dispose();
        });
        closeButton.addActionListener(event -> dispose());

        setTitle("消息详情");
        setSize(500, 350);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }
}
