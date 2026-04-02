package com.campuspet.service;

import com.campuspet.dao.PetDao;
import com.campuspet.entity.Pet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetService {
    private final PetDao petDao = new PetDao();

    public List<Pet> searchPets(String keyword, String type, String gender, String health, String status, String area) {
        try {
            return petDao.search(keyword, type, gender, health, status, area);
        } catch (SQLException e) {
            throw new IllegalStateException("动物数据加载失败，请检查数据库连接");
        }
    }

    public List<String> listAreas() {
        try {
            List<String> areas = new ArrayList<>();
            areas.add("全部");
            areas.addAll(petDao.listAreas());
            return areas;
        } catch (SQLException e) {
            List<String> defaultAreas = new ArrayList<>();
            defaultAreas.add("全部");
            defaultAreas.add("教学楼区");
            defaultAreas.add("宿舍区");
            defaultAreas.add("操场区");
            return defaultAreas;
        }
    }
}
