package com.campuspet.view.admin;

import com.campuspet.service.*;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private UserService userService = new UserService();
    private PetService petService = new PetService();
    private AdoptService adoptService = new AdoptService();
    private FeedPointService feedPointService = new FeedPointService();
    private ExceptionReportService exceptionService = new ExceptionReportService();

    public StatisticsPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titleLabel = new JLabel("统计分析");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.add(createStatCard("宠物总数", "待实现", "🐾"));
        contentPanel.add(createStatCard("领养申请数", "待实现", "📋"));
        contentPanel.add(createStatCard("通过率", "待实现", "✅"));
        contentPanel.add(createStatCard("投喂点数量", "待实现", "🍽️"));
        contentPanel.add(createStatCard("异常上报数", "待实现", "⚠️"));
        contentPanel.add(createStatCard("物资库存", "待实现", "📦"));

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value, String icon) {
        JPanel card = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        card.setPreferredSize(new Dimension(200, 100));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("微软雅黑", Font.BOLD, 32));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 100));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));

        infoPanel.add(titleLabel);
        infoPanel.add(valueLabel);

        card.add(iconLabel);
        card.add(infoPanel);

        return card;
    }
}
