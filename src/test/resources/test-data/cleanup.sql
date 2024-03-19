-- Delete data from all tables to have a fresh test environment ---

TRUNCATE TABLE transaction CASCADE;
TRUNCATE TABLE account CASCADE;
TRUNCATE TABLE "user" CASCADE;
TRUNCATE TABLE user_accounts CASCADE;
