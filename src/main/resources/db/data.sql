insert into users (username, psw, email, picture) values ('Matthew', 'Matthew', 'matthew@gmail.com', FILE_READ('classpath:images/matthew.jpg'));
insert into users (username, psw, email, picture) values ('Sam', 'Sam', 'sam@gmail.com', FILE_READ('classpath:images/sam.jpg'));
insert into users (username, psw, email, picture) values ('Chester', 'Chester', 'chester@gmail.com', FILE_READ('classpath:images/chester.jpg'));

insert into category (name) values ('clothes');
insert into category (name) values ('electronics');