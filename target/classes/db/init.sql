CREATE DATABASE IF NOT EXISTS campus_pet_system CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE campus_pet_system;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT '正常',
    create_time DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS pets (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pet_no VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50),
    type VARCHAR(20) NOT NULL,
    gender VARCHAR(10),
    age VARCHAR(20),
    photo VARCHAR(255),
    location VARCHAR(100),
    area VARCHAR(100),
    health VARCHAR(20),
    personality VARCHAR(20),
    rescue_story TEXT,
    status VARCHAR(20) NOT NULL,
    map_x INT,
    map_y INT,
    create_time DATETIME NOT NULL,
    update_time DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS adopt_apply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,
    applicant_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    experience VARCHAR(255) NOT NULL,
    reason TEXT,
    plan TEXT,
    apply_time DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    review_opinion VARCHAR(255),
    verify_record VARCHAR(255),
    agreement_signed VARCHAR(20) DEFAULT '未签署',
    review_time DATETIME
);

CREATE TABLE IF NOT EXISTS announcement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    publisher_id BIGINT NOT NULL,
    publisher_name VARCHAR(50) NOT NULL,
    publish_time DATETIME NOT NULL,
    update_time DATETIME
);

CREATE TABLE IF NOT EXISTS message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(500) NOT NULL,
    type VARCHAR(50) NOT NULL,
    is_read VARCHAR(10) NOT NULL DEFAULT '否',
    send_time DATETIME NOT NULL
);

INSERT INTO users (username, password, phone, role, status, create_time)
SELECT 'admin', 'admin123', '13800000000', '管理员', '正常', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, password, phone, role, status, create_time)
SELECT 'user01', '123456', '13800000001', '普通用户', '正常', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user01');

INSERT INTO users (username, password, phone, role, status, create_time)
SELECT 'vol01', '123456', '13800000002', '志愿者', '正常', NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'vol01');

INSERT INTO pets (pet_no, name, type, gender, age, photo, location, area, health, personality, rescue_story, status, map_x, map_y, create_time, update_time)
SELECT 'P001', '小橘', '猫', '母', '1岁', '', '教学楼后侧', '教学楼区', '健康', '温顺', '雨天在教学楼后被发现，后续由志愿者持续投喂。', '待领养', 180, 120, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE pet_no = 'P001');

INSERT INTO pets (pet_no, name, type, gender, age, photo, location, area, health, personality, rescue_story, status, map_x, map_y, create_time, update_time)
SELECT 'P002', '阿黄', '狗', '公', '2岁', '', '宿舍区北门', '宿舍区', '受伤', '活泼', '在宿舍区附近发现腿部受伤，已接受简单救助。', '救助中', 260, 220, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE pet_no = 'P002');

INSERT INTO pets (pet_no, name, type, gender, age, photo, location, area, health, personality, rescue_story, status, map_x, map_y, create_time, update_time)
SELECT 'P003', '小白', '猫', '公', '8个月', '', '操场东侧', '操场区', '健康', '胆小', '经常在操场边活动，目前已完成驱虫。', '已领养', 320, 180, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pets WHERE pet_no = 'P003');

INSERT INTO announcement (title, content, publisher_id, publisher_name, publish_time, update_time)
SELECT '领养须知', '请理性领养，确认具备稳定住所和照顾能力后再提交申请。', 1, 'admin', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM announcement WHERE title = '领养须知');
