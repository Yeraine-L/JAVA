package com.campuspet.dao;

import com.campuspet.entity.Pet;
import com.campuspet.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetDao {
    public List<Pet> search(String keyword, String type, String gender, String health, String status, String area) throws SQLException {
        StringBuilder sql = new StringBuilder("select id, pet_no, name, type, gender, age, photo, location, area, health, personality, rescue_story, status from pets where 1 = 1");
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" and (name like ? or rescue_story like ? or location like ?)");
            String keywordValue = "%" + keyword.trim() + "%";
            parameters.add(keywordValue);
            parameters.add(keywordValue);
            parameters.add(keywordValue);
        }
        if (!isAll(type)) {
            sql.append(" and type = ?");
            parameters.add(type);
        }
        if (!isAll(gender)) {
            sql.append(" and gender = ?");
            parameters.add(gender);
        }
        if (!isAll(health)) {
            sql.append(" and health = ?");
            parameters.add(health);
        }
        if (!isAll(status)) {
            sql.append(" and status = ?");
            parameters.add(status);
        }
        if (!isAll(area)) {
            sql.append(" and area = ?");
            parameters.add(area);
        }
        sql.append(" order by id asc");

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Pet> pets = new ArrayList<>();
                while (resultSet.next()) {
                    pets.add(mapPet(resultSet));
                }
                return pets;
            }
        }
    }

    public List<String> listAreas() throws SQLException {
        String sql = "select distinct area from pets where area is not null and area <> '' order by area asc";
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<String> areas = new ArrayList<>();
            while (resultSet.next()) {
                areas.add(resultSet.getString("area"));
            }
            return areas;
        }
    }

    private boolean isAll(String value) {
        return value == null || value.trim().isEmpty() || "全部".equals(value);
    }

    private Pet mapPet(ResultSet resultSet) throws SQLException {
        Pet pet = new Pet();
        pet.setId(resultSet.getLong("id"));
        pet.setPetNo(resultSet.getString("pet_no"));
        pet.setName(resultSet.getString("name"));
        pet.setType(resultSet.getString("type"));
        pet.setGender(resultSet.getString("gender"));
        pet.setAge(resultSet.getString("age"));
        pet.setPhoto(resultSet.getString("photo"));
        pet.setLocation(resultSet.getString("location"));
        pet.setArea(resultSet.getString("area"));
        pet.setHealth(resultSet.getString("health"));
        pet.setPersonality(resultSet.getString("personality"));
        pet.setRescueStory(resultSet.getString("rescue_story"));
        pet.setStatus(resultSet.getString("status"));
        return pet;
    }
}
