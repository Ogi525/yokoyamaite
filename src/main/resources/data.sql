-- =========================
-- CATEGORIES（固定ID OK）
-- =========================
INSERT INTO categories (id, category_name) VALUES
(1,'肉'),(2,'海鮮'),(3,'野菜'),(4,'お菓子'),(5,'飲料水'),(6,'お酒')
ON CONFLICT DO NOTHING;

-- =========================
-- AREAS（固定ID OK）
-- =========================
INSERT INTO areas (id, name) VALUES
(1,'道南'),(2,'道北'),(3,'道央'),(4,'道東')
ON CONFLICT DO NOTHING;


-- =========================
-- PRODUCTS（ID削除）
-- =========================
INSERT INTO products (
    area_id, category_id, name, price, stock,
    origin, image_url, description, video_url
)
SELECT
    (RANDOM()*3+1)::INT,
    (RANDOM()*5+1)::INT,
    '商品' || gs,
    (RANDOM()*5000+500)::INT,
    (RANDOM()*100)::INT,
    '北海道',
    'https://example.com/image' || gs || '.jpg',
    '説明' || gs,
    'https://example.com/video' || gs
FROM generate_series(1,20) gs
ON CONFLICT DO NOTHING;

-- =========================
-- COUPONS
-- =========================
INSERT INTO coupons (code, name, discount_value)
VALUES 
('BIG1000', '1000円引き', 1000),
('WIN500', '500円引き', 500),
('GOLDENRINZARASHI','景品プレゼント',1)
ON CONFLICT (code) DO NOTHING;


 