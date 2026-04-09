package com.campuspet.view.admin;

import com.campuspet.entity.Goods;
import com.campuspet.entity.User;
import com.campuspet.service.GoodsService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InStockDialog extends JDialog {
    private User user;
    private GoodsService service = new GoodsService();

    public InStockDialog(JFrame owner, User user) {
        super(owner, "物资入库", true);
        this.user = user;

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("物资:"), gbc);

        gbc.gridx = 1;
        JComboBox<Goods> goodsCombo = new JComboBox<>();
        goodsCombo.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        loadGoods(goodsCombo);
        rootPanel.add(goodsCombo, gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("数量:"), gbc);

        gbc.gridx = 1;
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        quantitySpinner.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(quantitySpinner, gbc);

        gbc.gridy = 2;
        rootPanel.add(new JLabel("备注:"), gbc);

        gbc.gridx = 1;
        JTextField remarkField = new JTextField(30);
        remarkField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(remarkField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton inButton = new JButton("入库");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(inButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        inButton.addActionListener(event -> handleInStock(goodsCombo, quantitySpinner, remarkField));
        cancelButton.addActionListener(event -> dispose());

        setTitle("物资入库 - " + user.getUsername());
        setSize(500, 250);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private void loadGoods(JComboBox<Goods> combo) {
        try {
            List<Goods> goodsList = service.getAll();
            for (Goods g : goodsList) {
                combo.addItem(g);
            }
        } catch (Exception e) {
            MessageUtil.error(this, "加载物资列表失败: " + e.getMessage());
        }
    }

    private void handleInStock(JComboBox<Goods> goodsCombo, JSpinner quantitySpinner, JTextField remarkField) {
        Goods goods = (Goods) goodsCombo.getSelectedItem();
        Integer quantity = (Integer) quantitySpinner.getValue();
        String remark = remarkField.getText().trim();

        try {
            if (goods == null) {
                throw new IllegalArgumentException("请选择物资");
            }

            service.inStock(goods.getId(), quantity, user, remark);
            MessageUtil.info(this, "物资入库成功");
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.error(this, e.getMessage());
        }
    }
}
