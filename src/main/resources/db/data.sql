truncate table users cascade;
truncate table medic cascade;


insert into users(id ,email ,password ) values
   (200, 'ridrijulmi@gufum.com' ,'password' ),
   (201, 'jostuyurzo@gufum.com' ,'password' ),
   (202, 'lepsopogno@gufum.com' ,'$2a$10$D4z0FTvjGyJFIgD/g6duU.eR9Iouui6lAG96CN2SXkPniup2mktse');

insert into medic(id, email, photo_url, specialization, license_number) values
    (300, 'ridrijulmi@gufum.com',  'photo', 'doctor', '400'),
    (301, 'jostuyurzo@gufum.com',  'photo', 'nurse', '401');