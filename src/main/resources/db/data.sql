truncate table users cascade;

insert into users(id,email,password) values
(200,'ridrijulmi@gufum.com', 'password'),
(201,'jostuyurzo@gufum.com', 'password'),
(202,'lepsopogno@gufum.com', '$2a$10$D4z0FTvjGyJFIgD/g6duU.eR9Iouui6lAG96CN2SXkPniup2mktse');

-- INSERT INTO emergency_request (user_id, description, address_id, request_time, request_status, medic_id, medical_report_id)
-- VALUES
--     (1, 'Severe headache and dizziness', 1, NOW(), 'PENDING', 1, 1),
--     (2, 'High fever and weakness', 2, NOW(), 'IN_PROGRESS', 2, 2),
--     (3, 'Chest pain and shortness of breath', 3, NOW(), 'COMPLETED', 3, 3),
--     (4, 'Persistent cough and sore throat', 4, NOW(), 'PENDING', 4, 4),
--     (5, 'Allergic reaction with swelling', 5, NOW(), 'IN_PROGRESS', 5, 5);

