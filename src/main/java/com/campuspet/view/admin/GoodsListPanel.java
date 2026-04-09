package com.campuspet.view.admin;

import com.campuspet.entity.Goods;
import com.campuspet.entity.User;
import com.campuspet.service.GoodsService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GoodsListPanel extends JPanel {
    private User user;
    private GoodsService service = new GoodsService();
    private List<Goods> goodsList;
    private JTable table;
    private DefaultTableModel tableModel;

    public GoodsListPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("物资管理");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("新增物资");
        JButton editButton = new JButton("编辑");
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(refreshButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"物资名称", "类型", "单位", "库存数量", "最低库存"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(event -> showAddDialog());
        editButton.addActionListener(event -> showEditDialog());
        refreshButton.addActionListener(event -> loadGoods());

        loadGoods();
    }

    private void loadGoods() {
        tableModel.setRowCount(0);
        try {
            goodsList = service.getAll();
            for (Goods g : goodsList) {
                tableModel.addRow(new Object[]{
                        g.getName(),
                        g.getType(),
                        g.getUnit(),
                        g.getTotalQuantity(),
                        g.getMinQuantity()
                });
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载物资列表失败: " + e.getMessage());
        }
    }

    private void showAddDialog() {
        new AddGoodsDialog((JFrame) SwingUtilities.getWindowAncestor(this), user).setVisible(true);
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageUtil.warn(this, "请先选择一条物资");
            return;
        }

        Goods goods = goodsList.get(selectedRow);
        new EditGoodsDialog((JFrame) SwingUtilities.getWindowAncestor(this), user, goods).setVisible(true);
    }
}
