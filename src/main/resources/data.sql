-- Insert products
INSERT INTO product (id, name, price)
VALUES (1, 'iPhone', 100),
       (2, 'Headphones', 20),
       (3, 'Case', 10);

-- Insert coupons
INSERT INTO coupon (id, code, discount, is_percentage)
VALUES (4, 'D15', 15, TRUE),
       (5, 'P10', 10, TRUE),
       (6, 'F5', 5, FALSE);

-- Insert taxes
INSERT INTO tax (id, region, rate)
VALUES (7, 'DE', 0.19),
       (8, 'IT', 0.22),
       (9, 'FR', 0.20),
       (10, 'GR', 0.24);
