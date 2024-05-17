-- base-data.sql

-- Insert base data for tortoise habitats
INSERT INTO tortoise_habitat (uuid, name)
VALUES ('11111111-1111-1111-1111-111111111111', 'Habitat1'),
       ('22222222-2222-2222-2222-222222222222', 'Habitat2');

-- Insert base data for tortoises
INSERT INTO tortoise (uuid, name, species, age, health_status, habitat_uuid)
VALUES ('33333333-3333-3333-3333-333333333333', 'Tortoise1', 'HERMANN', 5, 'Healthy',
        '11111111-1111-1111-1111-111111111111'),
       ('44444444-4444-4444-4444-444444444444', 'Tortoise2', 'HERMANN', 3, 'Healthy',
        '22222222-2222-2222-2222-222222222222'),
       ('55555555-5555-5555-5555-555555555555', 'Tortoise3', 'HERMANN', 2, 'Healthy',
        '11111111-1111-1111-1111-111111111111');

-- Insert base data for activity logs
INSERT INTO activity_log (uuid, tortoise_id, activity_type, start_time, end_time, notes)
VALUES ('66666666-6666-6666-6666-666666666666', '33333333-3333-3333-3333-333333333333', 'FEEDING',
        '2024-01-01T10:00:00', '2024-01-01T10:30:00', 'Feeding time'),
       ('77777777-7777-7777-7777-777777777777', '44444444-4444-4444-4444-444444444444', 'BASKING',
        '2024-01-01T11:00:00', '2024-01-01T11:30:00', 'Basking time'),
       ('88888888-8888-8888-8888-888888888888', '55555555-5555-5555-5555-555555555555', 'SLEEPING',
        '2024-01-01T12:00:00', '2024-01-01T12:30:00', 'Sleeping time'),
       ('99999999-9999-9999-9999-999999999999', '33333333-3333-3333-3333-333333333333', 'EXPLORING',
        '2024-01-01T13:00:00', '2024-01-01T13:30:00', 'Exploring time'),
       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 'SWIMMING',
        '2024-01-01T14:00:00', '2024-01-01T14:30:00', 'Swimming time');

-- Insert base data for environmental conditions
INSERT INTO environmental_condition (uuid, habitat_id, temperature, humidity, light_level, timestamp)
VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', 20.0, 65.0, 10.0,
        '2024-01-01T10:00:00'),
       ('cccccccc-cccc-cccc-cccc-cccccccccccc', '22222222-2222-2222-2222-222222222222', 22.0, 70.0, 12.0,
        '2024-01-02T10:00:00'),
       ('dddddddd-dddd-dddd-dddd-dddddddddddd', '11111111-1111-1111-1111-111111111111', 24.0, 75.0, 14.0,
        '2024-01-03T10:00:00'),
       ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '22222222-2222-2222-2222-222222222222', 26.0, 80.0, 16.0,
        '2024-01-04T10:00:00');
