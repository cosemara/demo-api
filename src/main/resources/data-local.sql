insert into `user`(`user_id`, `email`, `username`, `password`, `role`, `created_date`, `updated_date`, `deleted_date`)
values (1, 'test1@test.com', '김가루', '$2a$10$2bDrUQQFB.nSTJMkAcR6yOvCXvWRGgfT.a5ZrFdUefc7OqqsdtSpq', 'ROLE_USER', now(), now(), null),
       (2, 'test2@test.com', '김부각', '$2a$10$2bDrUQQFB.nSTJMkAcR6yOvCXvWRGgfT.a5ZrFdUefc7OqqsdtSpq', 'ROLE_USER', now(), now(), null),
       (3, 'test3@test.com', '김장철', '$2a$10$2bDrUQQFB.nSTJMkAcR6yOvCXvWRGgfT.a5ZrFdUefc7OqqsdtSpq', 'ROLE_USER', now(), now(), now());

insert into `assets`(`assets_id`, `user_id`, `assets_type`, `amount`, `created_date`, `updated_date`)
values (1, 1, 'COIN', '10000', now(), now()),
       (2, 2, 'COIN', '10000', now(), now()),
       (3, 3, 'COIN', '10000', now(), now());

insert into `assets_tx_history`(`trns_hist_id`, `user_id`, `assets_type`, `assets_tx_type` ,`amount`, `tx_date`, `created_date`, `updated_date`)
values (1, 1, 'COIN', 'BUY', '10000', now(), now(), now()),
       (2, 1, 'COIN', 'SELL', '9000', now(), now(), now()),
       (3, 1, 'COIN', 'BUY', '10000', now(), now(), now()),
       (4, 1, 'COIN', 'BUY', '10000', now(), now(), now()),
       (5, 1, 'COIN', 'SELL', '20000', now(), now(), now()),
       (6, 1, 'COIN', 'SELL', '9000', now(), now(), now()),
       (7, 1, 'COIN', 'BUY', '10000', now(), now(), now()),
       (8, 1, 'COIN', 'SELL', '9000', now(), now(), now()),
       (9, 1, 'COIN', 'BUY', '10000', now(), now(), now()),
       (10, 1, 'COIN', 'SELL', '9000', now(), now(), now()),
       (11, 1, 'COIN', 'BUY', '10000', now(), now(), now()),
       (12, 1, 'COIN', 'SELL', '9000', now(), now(), now()),
       (13, 1, 'COIN', 'BUY', '10000', now(), now(), now()),
       (14, 1, 'COIN', 'BUY', '10000', now(), now(), now());