DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(200) NOT NULL
);

CREATE TABLE userinformation (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  date_of_birth DATE NOT NULL
);

CREATE TABLE role (
  roleName VARCHAR(200),
  user_id INT NOT NULL
);







