create table cart_items
(
    id         bigint generated by default as identity,
    product_id bigint not null,
    cart_id    bigint,
    primary key (id)
);

create table carts
(
    id             bigint generated by default as identity,
    total_in_cents bigint not null check (total_in_cents >= 0),
    primary key (id)
);

create table products
(
    id             bigint generated by default as identity,
    name           varchar(255),
    price_in_cents bigint not null check (price_in_cents >= 1),
    primary key (id)
);

create table recipes
(
    id   bigint generated by default as identity,
    name varchar(255),
    primary key (id)
);

create table recipes_products
(
    recipe_id   bigint not null,
    product_id bigint not null
);

alter table cart_items
    add constraint FK1re40cjegsfvw58xrkdp6bac6 foreign key (product_id) references products;

alter table cart_items
    add constraint FKpcttvuq4mxppo8sxggjtn5i2c foreign key (cart_id) references carts;

alter table recipes_products
    add constraint FKsv87r1c7jponwvn5jtcy3wdiw foreign key (product_id) references products;

alter table recipes_products
    add constraint FKsiaie72nuy0xhsjhmewt63j1d foreign key (recipe_id) references recipes;