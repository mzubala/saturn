INSERT INTO products (name, price_in_cents) VALUES
    ('Eggs', 20),
    ('Butter', 50),
    ('Ham', 100),
    ('Tomatoes', 67)
;

INSERT INTO carts (total_in_cents) VALUES
    (0),
    (0)
;

INSERT INTO recipes (name) VALUES
    ('Scrumbled Eggs');

INSERT INTO recipes_products (recipe_id, product_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4);

