package com.campuspet.view.user;

import com.campuspet.entity.Message;
import com.campuspet.entity.User;
import com.campuspet.service.MessageService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MessageCenterPanel extends JPanel {
    private User user;
    private MessageService service = new MessageService();
    private List<Message> messages;
    private JTable table;
    private DefaultTableModel tableModel;

    public MessageCenterPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("消息中心");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton markReadButton = new JButton("标记已读");
        JButton markAllButton = new JButton("全部已读");
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(markReadButton);
        buttonPanel.add(markAllButton);
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"标题", "内容", "类型", "发送时间", "状态"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        markReadButton.addActionListener(event -> markAsRead());
        markAllButton.addActionListener(event -> markAllAsRead());
        refreshButton.addActionListener(event -> loadMessages());

        loadMessages();
    }

    private void loadMessages() {
        tableModel.setRowCount(0);
        try {
            messages = service.getMyMessages(user.getId());
            for (Message msg : messages) {
                tableModel.addRow(new Object[]{
                        msg.getTitle(),
                        msg.getContent(),
                        msg.getType(),
                        msg.getSendTime(),
                        "是".equals(msg.getIsRead()) ? "已读" : "未读"
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载消息列表失败: " + e.getMessage());
        }
    }

    private void markAsRead() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageUtil.warn(this, "请先选择一条消息");
            return;
        }

        try {
            service.markAsRead(messages.get(selectedRow).getId());
            MessageUtil.info(this, "消息已标记为已读");
            loadMessages();
        } catch (Exception e) {
            MessageUtil.error(this, "标记已读失败: " + e.getMessage());
        }
    }

    private void markAllAsRead() {
        int confirm = JOptionPane.showConfirmDialog(this, "确定要将所有消息标记为已读吗？", "确认操作", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.markAllAsRead(user.getId());
                MessageUtil.info(this, "所有消息已标记为已读");
                loadMessages();
            } catch (Exception e) {
                MessageUtil.error(this, "全部标记已读失败: " + e.getMessage());
            }
        }
    }
}
