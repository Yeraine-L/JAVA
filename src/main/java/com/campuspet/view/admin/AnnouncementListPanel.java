package com.campuspet.view.admin;

import com.campuspet.entity.Announcement;
import com.campuspet.entity.User;
import com.campuspet.service.AnnouncementService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AnnouncementListPanel extends JPanel {
    private User user;
    private AnnouncementService service = new AnnouncementService();
    private List<Announcement> announcements;
    private JTable table;
    private DefaultTableModel tableModel;

    public AnnouncementListPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("公告管理");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("新增");
        JButton editButton = new JButton("编辑");
        JButton deleteButton = new JButton("删除");
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"标题", "发布时间", "发布人"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(event -> showPublishDialog());
        editButton.addActionListener(event -> showEditDialog());
        deleteButton.addActionListener(event -> deleteAnnouncement());
        refreshButton.addActionListener(event -> loadAnnouncements());

        loadAnnouncements();
    }

    private void loadAnnouncements() {
        tableModel.setRowCount(0);
        try {
            announcements = service.getAll();
            for (Announcement a : announcements) {
                tableModel.addRow(new Object[]{
                        a.getTitle(),
                        a.getPublishTime(),
                        a.getPublisherName()
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载公告列表失败: " + e.getMessage());
        }
    }

    private void showPublishDialog() {
        new PublishAnnouncementDialog((JFrame) SwingUtilities.getWindowAncestor(this), user).setVisible(true);
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageUtil.warn(this, "请先选择一条公告");
            return;
        }

        Announcement announcement = announcements.get(selectedRow);
        new EditAnnouncementDialog((JFrame) SwingUtilities.getWindowAncestor(this), user, announcement).setVisible(true);
    }

    private void deleteAnnouncement() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageUtil.warn(this, "请先选择一条公告");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除这条公告吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.delete(announcements.get(selectedRow).getId());
                MessageUtil.info(this, "公告删除成功");
                loadAnnouncements();
            } catch (Exception e) {
                MessageUtil.error(this, "删除公告失败: " + e.getMessage());
            }
        }
    }
}
