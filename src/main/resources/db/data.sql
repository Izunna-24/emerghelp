truncate table users cascade;
truncate table medic cascade;


insert into users(id ,is_enabled ,email ,password ) values
   (200,true,'ridrijulmi@gufum.com' ,'password' ),
   (201,true,'jostuyurzo@gufum.com' ,'password' ),
   (202,true,'lepsopogno@gufum.com', '$2a$10$D4z0FTvjGyJFIgD/g6duU.eR9Iouui6lAG96CN2SXkPniup2mktse');

insert into medic(id, email, is_enabled , photo_url, specialization, license_number) values
    (300, 'ridrijulmi@gufum.com', true, 'photo', 'doctor', '400'),
    (301, 'jostuyurzo@gufum.com', true, 'photo', 'nurse', '401');

