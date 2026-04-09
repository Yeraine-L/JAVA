package com.campuspet.view.user;

import com.campuspet.entity.AdoptApply;
import com.campuspet.entity.User;

import javax.swing.*;
import java.awt.*;

public class AdoptFeedbackPanel extends JPanel {
    public AdoptFeedbackPanel(User user) {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel("领养回访记录");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel infoLabel = new JLabel("领养回访功能待实现");
        infoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(infoLabel);

        add(contentPanel, BorderLayout.CENTER);
    }
}
