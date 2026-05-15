-- CATEGORIES
INSERT INTO categories (id, category_name) VALUES
(1,'肉'),(2,'海鮮'),(3,'野菜'),(4,'お菓子'),(5,'飲料水'),(6,'お酒')
ON CONFLICT DO NOTHING;

-- AREAS
INSERT INTO areas (id, name) VALUES
(1,'道南'),(2,'道北'),(3,'道央'),(4,'道東')
ON CONFLICT DO NOTHING;

INSERT INTO users (id, name, email, password, postal_code, address)
SELECT
    gs,
    'ユーザー' || gs,
    'user' || gs || '@test.com',
    'password',
    LPAD((1000000 + (RANDOM()*8999999)::INT)::TEXT, 7, '0'),
    '北海道札幌市'
FROM generate_series(1,20) gs
ON CONFLICT DO NOTHING;

INSERT INTO products (
    id, area_id, category_id, name, price, stock,
    origin, image_url, description, video_url
)
SELECT
    gs,
    (RANDOM()*3+1)::INT,
    (RANDOM()*5+1)::INT,
    '商品' || gs,
    (RANDOM()*5000+500)::INT,
    (RANDOM()*100)::INT,  -- ★ stock
    '北海道',
    'https://example.com/image' || gs || '.jpg',
    '説明' || gs,
    'https://example.com/video' || gs
FROM generate_series(1,20) gs
ON CONFLICT DO NOTHING;

INSERT INTO carts (id, user_id)
SELECT gs, gs
FROM generate_series(1,20) gs
ON CONFLICT DO NOTHING;

INSERT INTO carts_products (cart_id, product_id, quantity)
SELECT
    (RANDOM()*19+1)::INT,
    (RANDOM()*19+1)::INT,
    (RANDOM()*5+1)::INT
FROM generate_series(1,20)
ON CONFLICT DO NOTHING;

INSERT INTO orders (id, user_id, total_price)
SELECT
    gs,
    (RANDOM()*19+1)::INT,
    (RANDOM()*10000+1000)::INT
FROM generate_series(1,20) gs
ON CONFLICT DO NOTHING;

INSERT INTO orders_products (order_id, product_id, price, quantity)
SELECT
    (RANDOM()*19+1)::INT,
    (RANDOM()*19+1)::INT,
    (RANDOM()*5000+500)::INT,
    (RANDOM()*5+1)::INT
FROM generate_series(1,20)
ON CONFLICT DO NOTHING;

INSERT INTO reviews (user_id, product_id, rating, comment)
SELECT
    (RANDOM()*19+1)::INT,
    (RANDOM()*19+1)::INT,
    (RANDOM()*4+1)::INT,
    '良い商品！'
FROM generate_series(1,20)
ON CONFLICT DO NOTHING;

INSERT INTO forum_posts (user_id, title, body)
SELECT
    (RANDOM()*19+1)::INT,
    'タイトル' || gs,
    '本文' || gs
FROM generate_series(1,20) gs
ON CONFLICT DO NOTHING;

INSERT INTO coupons (id, code, name, discount_value, min_price)
SELECT
    gs,
    'CODE' || gs,
    'クーポン' || gs,
    (RANDOM()*1000+100)::INT,
    (RANDOM()*5000)::INT
FROM generate_series(1,20) gs
ON CONFLICT DO NOTHING;

INSERT INTO users_coupons (user_id, coupon_id, is_used)
SELECT
    (RANDOM()*19+1)::INT,
    (RANDOM()*19+1)::INT,
    (RANDOM()>0.5)
FROM generate_series(1,20)
ON CONFLICT DO NOTHING;

INSERT INTO game_results (user_id, result, played_at)
SELECT
    gs,  -- user_id（1〜20）
    CASE 
        WHEN RANDOM() > 0.7 THEN '当たり'
        ELSE 'はずれ'
    END,
    CURRENT_DATE - (RANDOM()*10)::INT  -- 過去10日以内でランダム
FROM generate_series(1,20) gs;