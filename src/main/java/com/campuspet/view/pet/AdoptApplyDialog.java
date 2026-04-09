package com.campuspet.view.pet;

import com.campuspet.entity.AdoptApply;
import com.campuspet.entity.Pet;
import com.campuspet.entity.User;
import com.campuspet.service.AdoptService;
import com.campuspet.utils.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AdoptApplyDialog extends JDialog {
    private final Pet pet;
    private final User user;
    private final JTextField nameField = new JTextField(18);
    private final JTextField phoneField = new JTextField(18);
    private final JTextField addressField = new JTextField(18);
    private final JComboBox<String> experienceBox = new JComboBox<>(new String[]{"是", "否"});
    private final JTextArea reasonArea = new JTextArea(4, 20);
    private final JTextArea planArea = new JTextArea(4, 20);
    private final AdoptService adoptService = new AdoptService();

    public AdoptApplyDialog(Frame owner, Pet pet, User user) {
        super(owner, "领养申请 - " + pet.getName(), true);
        this.pet = pet;
        this.user = user;

        JPanel rootPanel = new JPanel(new GridBagLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rootPanel.add(new JLabel("领养申请"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        rootPanel.add(new JLabel("姓名："), gbc);
        gbc.gridx = 1;
        rootPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        rootPanel.add(new JLabel("手机号："), gbc);
        gbc.gridx = 1;
        rootPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        rootPanel.add(new JLabel("居住地址："), gbc);
        gbc.gridx = 1;
        rootPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        rootPanel.add(new JLabel("饲养经验："), gbc);
        gbc.gridx = 1;
        rootPanel.add(experienceBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        rootPanel.add(new JLabel("领养原因："), gbc);
        gbc.gridx = 1;
        rootPanel.add(new JScrollPane(reasonArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        rootPanel.add(new JLabel("饲养计划："), gbc);
        gbc.gridx = 1;
        rootPanel.add(new JScrollPane(planArea), gbc);

        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("提交申请");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        rootPanel.add(buttonPanel, gbc);

        submitButton.addActionListener(event -> handleSubmit());
        cancelButton.addActionListener(event -> dispose());

        setTitle("领养申请 - " + pet.getName());
        setSize(500, 520);
        setLocationRelativeTo(owner);
        setContentPane(rootPanel);
    }

    private void handleSubmit() {
        try {
            adoptService.apply(
                    user,
                    pet,
                    nameField.getText(),
                    phoneField.getText(),
                    addressField.getText(),
                    String.valueOf(experienceBox.getSelectedItem()),
                    reasonArea.getText(),
                    planArea.getText()
            );
            dispose();
        } catch (IllegalArgumentException e) {
            MessageUtil.warn(this, e.getMessage());
        } catch (IllegalStateException e) {
            MessageUtil.warn(this, e.getMessage());
        }
    }
}
