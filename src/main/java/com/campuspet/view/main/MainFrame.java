package com.campuspet.view.main;

import com.campuspet.config.AppConfig;
import com.campuspet.entity.User;
import com.campuspet.view.admin.AdoptReviewPanel;
import com.campuspet.view.pet.PetListPanel;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;

public class MainFrame extends JFrame {
    public MainFrame(User user) {
        setTitle(AppConfig.APP_NAME);
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("当前用户：" + user.getUsername() + "    角色：" + user.getRole().getDisplayName());
        headerLabel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        add(headerLabel, BorderLayout.NORTH);

        DefaultListModel<String> menuModel = new DefaultListModel<>();
        buildMenu(menuModel, user);
        JList<String> menuList = new JList<>(menuModel);
        menuList.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        JScrollPane menuScrollPane = new JScrollPane(menuList);
        menuScrollPane.setPreferredSize(new Dimension(220, 0));
        add(menuScrollPane, BorderLayout.WEST);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        registerPanels(cardPanel, user);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        menuList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                String selectedValue = menuList.getSelectedValue();
                if (selectedValue != null) {
                    cardLayout.show(cardPanel, selectedValue);
                }
            }
        });

        if (!menuModel.isEmpty()) {
            menuList.setSelectedIndex(0);
        }
    }

    private void buildMenu(DefaultListModel<String> menuModel, User user) {
        switch (user.getRole()) {
            case USER:
                menuModel.addElement("动物信息");
                menuModel.addElement("校园地图");
                menuModel.addElement("我的申请");
                menuModel.addElement("消息通知");
                menuModel.addElement("个人中心");
                menuModel.addElement("公告查看");
                break;
            case VOLUNTEER:
                menuModel.addElement("动物信息");
                menuModel.addElement("校园地图");
                menuModel.addElement("投喂点申请");
                menuModel.addElement("投喂记录");
                menuModel.addElement("异常上报");
                menuModel.addElement("物资管理");
                menuModel.addElement("消息通知");
                menuModel.addElement("个人中心");
                menuModel.addElement("公告查看");
                break;
            case ADMIN:
                menuModel.addElement("动物管理");
                menuModel.addElement("领养审核");
                menuModel.addElement("投喂点管理");
                menuModel.addElement("异常处理");
                menuModel.addElement("物资管理");
                menuModel.addElement("公告管理");
                menuModel.addElement("用户管理");
                menuModel.addElement("统计分析");
                menuModel.addElement("系统日志");
                break;
            default:
                break;
        }
    }

    private String buildWelcomeText(User user) {
        return "欢迎进入" + AppConfig.APP_NAME + "。\n\n"
                + "当前已完成数据库登录注册和动物信息首版展示。\n\n"
                + "当前阶段可以继续扩展以下模块：\n"
                + "1. 领养申请与审核\n"
                + "2. 用户中心\n"
                + "3. 公告与消息通知\n"
                + "4. 投喂与物资管理\n\n"
                + "当前登录角色：" + user.getRole().getDisplayName() + "。\n"
                + "左侧菜单已支持内容切换，后续模块会逐步接入。";
    }

    private void registerPanels(JPanel cardPanel, User user) {
        switch (user.getRole()) {
            case USER:
                PetListPanel userPetPanel = new PetListPanel();
                userPetPanel.setCurrentUser(user);
                cardPanel.add(userPetPanel, "动物信息");
                cardPanel.add(createTextPanel("校园地图模块将在下一阶段接入静态图片标记功能。"), "校园地图");
                cardPanel.add(createTextPanel("我的申请模块将在领养申请与审核闭环完成后接入。"), "我的申请");
                cardPanel.add(createTextPanel("消息通知模块将在公告与审核结果联动后接入。"), "消息通知");
                cardPanel.add(createTextPanel(buildWelcomeText(user)), "个人中心");
                cardPanel.add(createTextPanel("公告查看模块将在公告管理完成后接入。"), "公告查看");
                break;
            case VOLUNTEER:
                PetListPanel volPetPanel = new PetListPanel();
                volPetPanel.setCurrentUser(user);
                cardPanel.add(volPetPanel, "动物信息");
                cardPanel.add(createTextPanel("校园地图模块将在下一阶段接入静态图片标记功能。"), "校园地图");
                cardPanel.add(createTextPanel("投喂点申请模块将在下一阶段开发。"), "投喂点申请");
                cardPanel.add(createTextPanel("投喂记录模块将在下一阶段开发。"), "投喂记录");
                cardPanel.add(createTextPanel("异常上报模块将在下一阶段开发。"), "异常上报");
                cardPanel.add(createTextPanel("物资管理模块将在下一阶段开发。"), "物资管理");
                cardPanel.add(createTextPanel("消息通知模块将在公告与审核结果联动后接入。"), "消息通知");
                cardPanel.add(createTextPanel(buildWelcomeText(user)), "个人中心");
                cardPanel.add(createTextPanel("公告查看模块将在公告管理完成后接入。"), "公告查看");
                break;
            case ADMIN:
                cardPanel.add(new PetListPanel(), "动物管理");
                cardPanel.add(new AdoptReviewPanel(), "领养审核");
                cardPanel.add(createTextPanel("投喂点管理模块将在下一阶段接入。"), "投喂点管理");
                cardPanel.add(createTextPanel("异常处理模块将在下一阶段接入。"), "异常处理");
                cardPanel.add(createTextPanel("物资管理模块将在下一阶段接入。"), "物资管理");
                cardPanel.add(createTextPanel("公告管理模块将在下一阶段接入。"), "公告管理");
                cardPanel.add(createTextPanel("用户管理模块将在下一阶段接入。"), "用户管理");
                cardPanel.add(createTextPanel("统计分析模块将在下一阶段接入 JFreeChart 图表。"), "统计分析");
                cardPanel.add(createTextPanel("系统日志模块将在下一阶段接入。"), "系统日志");
                break;
            default:
                break;
        }
    }

    private JScrollPane createTextPanel(String text) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(text);
        return new JScrollPane(textArea);
    }
}
