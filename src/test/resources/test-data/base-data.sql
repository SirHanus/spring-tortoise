INSERT INTO public."user" (id, name, username) VALUES (1, 'Ivo', 'ivo');
INSERT INTO public."user" (id, name, username) VALUES (2, 'Marie', 'mar777');

INSERT INTO public.account (id, balance, name, owner_id) VALUES (1, 100.00, 'My account', 1);
INSERT INTO public.account (id, balance, name, owner_id) VALUES (2, 200.00, 'Savings for a car', 2);

INSERT INTO public."user_accounts" (users_id, accounts_id) VALUES (1, 1);
INSERT INTO public."user_accounts" (users_id, accounts_id) VALUES (2, 2);
INSERT INTO public."user_accounts" (users_id, accounts_id) VALUES (1, 2);

INSERT INTO public.transaction (id, amount, source_account_id, target_account_id) VALUES ('fffd85db-55c5-4620-b7eb-73191a43533e', 50.00, 1, 2);
INSERT INTO public.transaction (id, amount, source_account_id, target_account_id) VALUES ('5fdba127-ab33-4881-bcf8-096e210fe7c9', 50.00, 2, 1);


INSERT INTO public.loan (id, amount, interest_rate, attached_account_id, start_date, period)
VALUES
    ('a1d0c6e8-ba87-4b2d-9197-77664b5e0c08', 500.00, 5.0, 1, CURRENT_DATE, 12),
    ('b2d0c6e8-ba87-4b2d-9197-77664b5e0c09', 1000.00, 4.5, 2, CURRENT_DATE, 24),
    ('c3d0c6e8-ba87-4b2d-9197-77664b5e0c10', 1500.00, 4.0, 2, CURRENT_DATE, 36);