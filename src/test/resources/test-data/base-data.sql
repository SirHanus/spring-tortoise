INSERT INTO public."user" (id, name, username) VALUES (1, 'Ivo', 'ivo');
INSERT INTO public."user" (id, name, username) VALUES (2, 'Marie', 'mar777');

INSERT INTO public.account (id, balance, name, owner_id) VALUES (1, 100.00, 'My account', 1);
INSERT INTO public.account (id, balance, name, owner_id) VALUES (2, 200.00, 'Savings for a car', 2);

INSERT INTO public."user_accounts" (users_id, accounts_id) VALUES (1, 1);
INSERT INTO public."user_accounts" (users_id, accounts_id) VALUES (2, 2);
INSERT INTO public."user_accounts" (users_id, accounts_id) VALUES (1, 2);