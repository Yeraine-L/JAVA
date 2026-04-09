package com.campuspet.view.common;

import javax.swing.*;
import java.awt.*;

public class CampusMapPanel extends JPanel {
    public CampusMapPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("校园地图");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(new JLabel("📍 投喂点标记"));
        infoPanel.add(new JLabel("🐾 宠物活动区域"));
        infoPanel.add(new JLabel("🏠 建筑物"));
        topPanel.add(infoPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(240, 240, 240));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(200, 200, 200));
                g2d.setStroke(new BasicStroke(2));
                for (int i = 50; i < getWidth(); i += 100) {
                    g2d.drawLine(i, 0, i, getHeight());
                }
                for (int i = 50; i < getHeight(); i += 100) {
                    g2d.drawLine(0, i, getWidth(), i);
                }

                g2d.setColor(new Color(100, 100, 100));
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRect(100, 100, 400, 300);
                g2d.drawString("教学楼", 250, 250);

                g2d.setColor(new Color(100, 100, 100));
                g2d.drawRect(600, 100, 300, 200);
                g2d.drawString("实验楼", 700, 200);

                g2d.setColor(new Color(100, 100, 100));
                g2d.drawRect(100, 500, 500, 200);
                g2d.drawString("学生宿舍", 300, 600);

                g2d.setColor(new Color(100, 100, 100));
                g2d.drawRect(650, 400, 250, 300);
                g2d.drawString("食堂", 750, 550);

                g2d.setColor(new Color(100, 100, 100));
                g2d.drawRect(950, 200, 200, 150);
                g2d.drawString("图书馆", 1000, 280);

                g2d.setColor(new Color(100, 100, 100));
                g2d.drawRect(900, 600, 300, 150);
                g2d.drawString("操场", 1000, 680);

                g2d.setColor(new Color(0, 150, 0));
                g2d.fillOval(300, 180, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(300, 180, 20, 20);
                g2d.drawString("投喂点1", 290, 210);

                g2d.setColor(new Color(0, 150, 0));
                g2d.fillOval(700, 150, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(700, 150, 20, 20);
                g2d.drawString("投喂点2", 690, 180);

                g2d.setColor(new Color(0, 150, 0));
                g2d.fillOval(400, 600, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(400, 600, 20, 20);
                g2d.drawString("投喂点3", 390, 630);

                g2d.setColor(new Color(0, 150, 0));
                g2d.fillOval(800, 500, 20, 20);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(800, 500, 20, 20);
                g2d.drawString("投喂点4", 790, 530);

                g2d.setColor(new Color(255, 100, 100));
                g2d.fillOval(200, 300, 30, 30);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(200, 300, 30, 30);
                g2d.drawString("猫活动区", 190, 340);

                g2d.setColor(new Color(255, 100, 100));
                g2d.fillOval(800, 300, 30, 30);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(800, 300, 30, 30);
                g2d.drawString("狗活动区", 790, 340);

                g2d.dispose();
            }
        };
        mapPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        mapPanel.setPreferredSize(new Dimension(1200, 800));
        add(mapPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomPanel.add(new JLabel("说明："));
        bottomPanel.add(new JLabel("🟢 绿色圆点 - 投喂点"));
        bottomPanel.add(new JLabel("🔴 红色圆点 - 宠物活动区域"));
        bottomPanel.add(new JLabel("⚫ 黑色方框 - 建筑物"));
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
