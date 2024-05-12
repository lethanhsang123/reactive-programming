CREATE TABLE public.users
(
    id      bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    "name"  varchar NULL,
    balance decimal NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);
CREATE TABLE public.user_transactions
(
    id               bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
    user_id          bigint                              NOT NULL,
    amount           decimal NULL,
    transaction_date timestamp NULL,
    CONSTRAINT user_transactions_pk PRIMARY KEY (id)
);
