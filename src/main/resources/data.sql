INSERT INTO product (id, name, price) VALUES (1, 'iPhone', 100);
INSERT INTO product (id, name, price) VALUES (2, 'Headphones', 20);
INSERT INTO product (id, name, price) VALUES (3, 'Case', 10);

-- Insert coupons
INSERT INTO coupon (id, code, discount, is_percentage) VALUES (1, 'P15', 15, TRUE);
INSERT INTO coupon (id, code, discount, is_percentage) VALUES (2, 'P10', 10, TRUE);
INSERT INTO coupon (id, code, discount, is_percentage) VALUES (3, 'F5', 5, FALSE);

INSERT INTO tax (id, region, rate) VALUES (1, 'DE', 0.19);
INSERT INTO tax (id, region, rate) VALUES (2, 'IT', 0.22);
INSERT INTO tax (id, region, rate) VALUES (3, 'FR', 0.20);
INSERT INTO tax (id, region, rate) VALUES (4, 'GR', 0.24);
