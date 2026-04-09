-- 投喂点表
CREATE TABLE IF NOT EXISTS feed_point (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    volunteer_id BIGINT NOT NULL,
    volunteer_name VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    area VARCHAR(100),
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT '待审核',
    apply_time DATETIME NOT NULL,
    review_time DATETIME,
    review_opinion VARCHAR(255)
);

-- 投喂记录表
CREATE TABLE IF NOT EXISTS feed_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    feed_point_id BIGINT NOT NULL,
    volunteer_id BIGINT NOT NULL,
    volunteer_name VARCHAR(50) NOT NULL,
    pet_count INT NOT NULL,
    food_amount DECIMAL(10,2) NOT NULL,
    food_type VARCHAR(50),
    feed_time DATETIME NOT NULL,
    create_time DATETIME NOT NULL
);

-- 异常上报表
CREATE TABLE IF NOT EXISTS exception_report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    volunteer_id BIGINT NOT NULL,
    volunteer_name VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    area VARCHAR(100),
    type VARCHAR(50) NOT NULL,
    description TEXT,
    photo VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT '待处理',
    handle_opinion TEXT,
    handle_time DATETIME,
    create_time DATETIME NOT NULL
);

-- 物资表
CREATE TABLE IF NOT EXISTS goods (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    total_quantity INT NOT NULL DEFAULT 0,
    min_quantity INT NOT NULL DEFAULT 10,
    create_time DATETIME NOT NULL
);

-- 物资记录表
CREATE TABLE IF NOT EXISTS goods_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    goods_id BIGINT NOT NULL,
    goods_name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    operator_id BIGINT NOT NULL,
    operator_name VARCHAR(50) NOT NULL,
    remark VARCHAR(255),
    create_time DATETIME NOT NULL
);

-- 初始化测试数据
INSERT INTO goods (name, type, unit, total_quantity, min_quantity, create_time)
SELECT '猫粮', '食品', '袋', 100, 20, NOW()
WHERE NOT EXISTS (SELECT 1 FROM goods WHERE name = '猫粮');

INSERT INTO goods (name, type, unit, total_quantity, min_quantity, create_time)
SELECT '狗粮', '食品', '袋', 80, 20, NOW()
WHERE NOT EXISTS (SELECT 1 FROM goods WHERE name = '狗粮');

INSERT INTO goods (name, type, unit, total_quantity, min_quantity, create_time)
SELECT '驱虫药', '药品', '盒', 50, 10, NOW()
WHERE NOT EXISTS (SELECT 1 FROM goods WHERE name = '驱虫药');

INSERT INTO goods (name, type, unit, total_quantity, min_quantity, create_time)
SELECT '猫砂', '用品', '袋', 60, 15, NOW()
WHERE NOT EXISTS (SELECT 1 FROM goods WHERE name = '猫砂');
