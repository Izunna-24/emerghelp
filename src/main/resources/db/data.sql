truncate table users cascade;
truncate table medic cascade;
truncate table admin cascade;

insert into users(id ,is_enabled ,email ,password,longitude,latitude, is_logged_in) values
   (200,true,'ridrijulmi@gufum.com' ,'password' ,3.377417,5.377417, false),
   (201,true,'jostuyurzo@gufum.com' ,'password',3.377417,5.377417, false),
   (202,true,'lepsopogno@gufum.com', '$2a$10$D4z0FTvjGyJFIgD/g6duU.eR9Iouui6lAG96CN2SXkPniup2mktse',3.377417,5.377417, false);

insert into medic(id, email, photo_url, specialization, license_number, longitude, latitude) values
    (300, 'ridrijulmi@gufum.com', 'photo', 'doctor', '400', 3.377417, 5.377417),
    (301, 'jostuyurzo@gufum.com', 'photo', 'nurse', '401', 3.377417, 5.377417);


INSERT INTO admin(id, email, password,user_name) values
    (502,'ganda@gmail.com','7788','Eric'),
    (503, 'jostuyurzo@gufum.com', '7883','Pat');

