package com.campuspet.view.admin;

import com.campuspet.entity.User;
import com.campuspet.service.GoodsService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class AddGoodsDialog extends JDialog {
    private User user;
    private GoodsService service = new GoodsService();

    public AddGoodsDialog(JFrame owner, User user) {
        super(owner, "新增物资", true);
        this.user = user;

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rootPanel.add(new JLabel("物资名称:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(30);
        nameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(nameField, gbc);

        gbc.gridy = 1;
        rootPanel.add(new JLabel("类型:"), gbc);

        gbc.gridx = 1;
        JTextField typeField = new JTextField(30);
        typeField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(typeField, gbc);

        gbc.gridy = 2;
        rootPanel.add(new JLabel("单位:"), gbc);

        gbc.gridx = 1;
        JTextField unitField = new JTextField(20);
        unitField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(unitField, gbc);

        gbc.gridy = 3;
        rootPanel.add(new JLabel("最低库存:"), gbc);

        gbc.gridx = 1;
        JSpinner minQuantitySpinner = new JSpinner(new SpinnerNumberModel(10, 1, 1000, 1));
        minQuantitySpinner.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rootPanel.add(minQuantitySpinner, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("添加");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        addButton.addActionListener(event -> handleAdd(nameField, typeField, unitField, minQuantitySpinner));
        cancelButton.addActionListener(event -> dispose());

        setTitle("新增物资 - " + user.getUsername());
        setSize(500, 300);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private void handleAdd(JTextField nameField, JTextField typeField, JTextField unitField, JSpinner minQuantitySpinner) {
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        String unit = unitField.getText().trim();
        Integer minQuantity = (Integer) minQuantitySpinner.getValue();

        try {
            service.addGoods(name, type, unit, minQuantity);
            MessageUtil.info(this, "物资添加成功");
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.error(this, e.getMessage());
        }
    }
}
