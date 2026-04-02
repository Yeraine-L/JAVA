package com.campuspet.view.pet;

import com.campuspet.entity.Pet;
import com.campuspet.service.PetService;
import com.campuspet.utils.MessageUtil;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class PetListPanel extends JPanel {
    private final JTextField keywordField = new JTextField();
    private final JComboBox<String> typeBox = new JComboBox<>(new String[]{"全部", "猫", "狗"});
    private final JComboBox<String> genderBox = new JComboBox<>(new String[]{"全部", "公", "母"});
    private final JComboBox<String> healthBox = new JComboBox<>(new String[]{"全部", "健康", "受伤", "生病", "待救助"});
    private final JComboBox<String> statusBox = new JComboBox<>(new String[]{"全部", "待领养", "已领养", "救助中"});
    private final JComboBox<String> areaBox = new JComboBox<>();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"编号", "名称", "种类", "性别", "年龄", "健康状况", "所在区域", "当前状态"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);
    private final PetService petService = new PetService();
    private final List<Pet> currentPets = new ArrayList<>();

    public PetListPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel filterPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        filterPanel.add(new JLabel("关键词"));
        filterPanel.add(keywordField);
        filterPanel.add(new JLabel("种类"));
        filterPanel.add(typeBox);
        filterPanel.add(new JLabel("性别"));
        filterPanel.add(genderBox);
        filterPanel.add(new JLabel("健康状态"));
        filterPanel.add(healthBox);
        filterPanel.add(new JLabel("领养状态"));
        filterPanel.add(statusBox);
        filterPanel.add(new JLabel("所在区域"));
        filterPanel.add(areaBox);

        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.add(filterPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("搜索");
        JButton resetButton = new JButton("重置");
        JButton detailButton = new JButton("查看详情");
        buttonPanel.add(searchButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(detailButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        searchButton.addActionListener(event -> loadPets());
        resetButton.addActionListener(event -> resetFilters());
        detailButton.addActionListener(event -> showSelectedPet());

        loadAreas();
        loadPets();
    }

    private void loadAreas() {
        areaBox.removeAllItems();
        for (String area : petService.listAreas()) {
            areaBox.addItem(area);
        }
    }

    private void loadPets() {
        try {
            List<Pet> pets = petService.searchPets(
                    keywordField.getText(),
                    getSelectedValue(typeBox),
                    getSelectedValue(genderBox),
                    getSelectedValue(healthBox),
                    getSelectedValue(statusBox),
                    getSelectedValue(areaBox)
            );
            currentPets.clear();
            currentPets.addAll(pets);
            refreshTable();
        } catch (IllegalStateException e) {
            MessageUtil.warn(this, e.getMessage());
        }
    }

    private void resetFilters() {
        keywordField.setText("");
        typeBox.setSelectedIndex(0);
        genderBox.setSelectedIndex(0);
        healthBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);
        areaBox.setSelectedIndex(0);
        loadPets();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Pet pet : currentPets) {
            tableModel.addRow(new Object[]{
                    pet.getPetNo(),
                    pet.getName(),
                    pet.getType(),
                    pet.getGender(),
                    pet.getAge(),
                    pet.getHealth(),
                    pet.getArea(),
                    pet.getStatus()
            });
        }
    }

    private void showSelectedPet() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            MessageUtil.warn(this, "请先选择一条动物信息");
            return;
        }

        Pet pet = currentPets.get(selectedRow);
        JTextArea detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setText(
                "动物编号：" + valueOf(pet.getPetNo()) + "\n"
                        + "名称：" + valueOf(pet.getName()) + "\n"
                        + "种类：" + valueOf(pet.getType()) + "\n"
                        + "性别：" + valueOf(pet.getGender()) + "\n"
                        + "年龄：" + valueOf(pet.getAge()) + "\n"
                        + "健康状况：" + valueOf(pet.getHealth()) + "\n"
                        + "性格：" + valueOf(pet.getPersonality()) + "\n"
                        + "发现位置：" + valueOf(pet.getLocation()) + "\n"
                        + "所在区域：" + valueOf(pet.getArea()) + "\n"
                        + "当前状态：" + valueOf(pet.getStatus()) + "\n"
                        + "救助故事：" + valueOf(pet.getRescueStory())
        );
        MessageUtil.info(this, detailArea.getText());
    }

    private String getSelectedValue(JComboBox<String> comboBox) {
        Object selected = comboBox.getSelectedItem();
        return selected == null ? "全部" : String.valueOf(selected);
    }

    private String valueOf(String value) {
        return value == null || value.trim().isEmpty() ? "暂无" : value;
    }
}
