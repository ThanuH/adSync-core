create database adsyncdb;

use adsyncdb;

CREATE TABLE business_category (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   category_name VARCHAR(255) NOT NULL
);

CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      contact_number VARCHAR(20) NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL,
                      business_registration_number VARCHAR(100) UNIQUE,
                      business_name VARCHAR(255),
                      business_category_id INT,
                      password VARCHAR(255) NOT NULL,
                      status VARCHAR(20),
                      FOREIGN KEY (business_category_id) REFERENCES business_category(id)
);

-- Insert two dummy records to the user table
insert into user (contact_number,email,business_registration_number,business_name,business_category_id,password,status)
values('0786352417','tuiter@hmail.com','PV98432838', 'Idea Computers', 3, 'admin123', 'u');

insert into user (contact_number,email,business_registration_number,business_name,business_category_id,password,status)
values('0766352414','jiter@fmail.com','PV98432833', 'Kris Medilabs', 4, 'jani123', 'u');

CREATE TABLE advertisement (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               advertisement_url VARCHAR(255) NOT NULL
);

CREATE TABLE user_advertisement (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   user_id INT,
                                   priority INT,
                                   targeted_age VARCHAR(50),
                                   targeted_audience VARCHAR(255),
                                   business_category_id INT,
                                   FOREIGN KEY (user_id) REFERENCES user(id),
                                   FOREIGN KEY (business_category_id) REFERENCES business_category(id)
);

INSERT INTO business_category (category_name) VALUES
                                                  ('Restaurants'),
                                                  ('Retail Stores'),
                                                  ('Technology Services'),
                                                  ('Healthcare'),
                                                  ('Finance'),
                                                  ('Real Estate'),
                                                  ('Education'),
                                                  ('Hospitality'),
                                                  ('Automotive'),
                                                  ('Construction'),
                                                  ('Legal Services'),
                                                  ('Entertainment'),
                                                  ('Fitness'),
                                                  ('Fashion'),
                                                  ('Travel'),
                                                  ('Food and Beverage'),
                                                  ('Beauty and Wellness'),
                                                  ('Home Services'),
                                                  ('Marketing and Advertising'),
                                                  ('Consulting');

CREATE TABLE user_role (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           role_description VARCHAR(255) NOT NULL
);

ALTER TABLE user
    ADD COLUMN user_role_id INT,
ADD CONSTRAINT fk_user_role
    FOREIGN KEY (user_role_id)
    REFERENCES user_role(id);

INSERT INTO user_role (role_description) VALUES ('admin');
INSERT INTO user_role (role_description) VALUES ('user');

alter table user_advertisement
    add status varchar(2) not null;


INSERT INTO advertisement (advertisement_url) VALUES
                                                  ('https://example.com/ad1'),
                                                  ('https://example.com/ad2'),
                                                  ('https://example.com/ad3'),
                                                  ('https://example.com/ad4'),
                                                  ('https://example.com/ad5');


-- Assuming your user and business_category tables are already populated

-- Inserting 20 dummy records into UserAdvertisement with status values 'R', 'S', and 'A'
INSERT INTO user_advertisement (user_id, priority, targeted_age, business_category_id, status, targeted_audience) VALUES
                                                                                                                      (1, 1, '18-25', 1, 'R', 'Male'),
                                                                                                                      (2, 1, '26-35',  4, 'S', 'Female'),
                                                                                                                      (1, 1, '36-45',  1, 'A', 'Both'),
                                                                                                                      (2, 1, '18-25',  4, 'R', 'Male'),
                                                                                                                      (1, 1, '26-35',  1, 'S', 'Female'),
                                                                                                                      (2, 1, '36-45',  4, 'A', 'Both'),
                                                                                                                      (1, 1, '18-25', 6, 'R', 'Male'),
                                                                                                                      (2, 1, '26-35',  4, 'S', 'Female'),
                                                                                                                      (1, 1, '36-45',  1, 'A', 'Both'),
                                                                                                                      (2, 1, '18-25',  4, 'R', 'Male'),
                                                                                                                      (1, 1, '26-35',  1, 'S', 'Female'),
                                                                                                                      (2, 1, '36-45', 4, 'A', 'Both'),
                                                                                                                      (1, 1, '18-25', 7,'R', 'Male'),
                                                                                                                      (2, 1, '26-35',  4, 'S', 'Female'),
                                                                                                                      (1, 1, '36-45',  4,'A', 'Both'),
                                                                                                                      (2, 1, '18-25',  4, 'R', 'Male'),
                                                                                                                      (1, 1, '26-35',  1, 'S', 'Female'),
                                                                                                                      (2, 1, '36-45',  4, 'A', 'Both'),
                                                                                                                      (1, 1, '18-25', 5, 'R', 'Male'),
                                                                                                                      (2, 1, '26-35',  4, 'S', 'Female');

CREATE TABLE reported_issues (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 user_id INT,
                                 issue_description VARCHAR(255) NOT NULL,
                                 status VARCHAR(2) NOT NULL,
                                 FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Assuming your user table is already populated

INSERT INTO reported_issues (user_id, issue_description, status) VALUES
                                                                     (1, 'Login problem', 'P'),
                                                                     (2, 'Payment issue', 'RS'),
                                                                     (1, 'Profile update error', 'P'),
                                                                     (2, 'App crashing', 'RS');

ALTER TABLE user_advertisement
    ADD COLUMN advertisement_id INT,
ADD CONSTRAINT fk_user_advertisement_advertisement
    FOREIGN KEY (advertisement_id) REFERENCES advertisement(id);


-- Dummy data for Advertisement table
INSERT INTO advertisement (advertisement_url) VALUES
                                                  ('https://example.com/ad1'),
                                                  ('https://example.com/ad2'),
                                                  ('https://example.com/ad3'),
                                                  ('https://example.com/ad4'),
                                                  ('https://example.com/ad5'),
                                                  ('https://example.com/ad6'),
                                                  ('https://example.com/ad7'),
                                                  ('https://example.com/ad8'),
                                                  ('https://example.com/ad9'),
                                                  ('https://example.com/ad10');

-- Dummy data for UserAdvertisement table
INSERT INTO user_advertisement (user_id, advertisement_id, priority, targeted_age, targeted_audience, business_category_id, status) VALUES
                                                                                                                                        (1, 1, 1, '18-35', 'Male', 1, 'P'),
                                                                                                                                        (4, 2, 2, '25-50', 'Female', 2, 'A'),
                                                                                                                                        (1, 3, 3, '18-35', 'Both', 3, 'R'),
                                                                                                                                        (4, 4, 4, '25-50', 'Male', 4, 'P'),
                                                                                                                                        (1, 5, 5, '18-35', 'Female', 5, 'A'),
                                                                                                                                        (4, 6, 6, '25-50', 'Both', 1, 'R'),
                                                                                                                                        (1, 7, 7, '18-35', 'Male', 2, 'P'),
                                                                                                                                        (4, 8, 8, '25-50', 'Female', 3, 'A'),
                                                                                                                                        (1, 9, 9, '18-35', 'Both', 4, 'R'),
                                                                                                                                        (4, 10, 10, '25-50', 'Male', 5, 'P'),
                                                                                                                                        (1, 11, 11, '18-35', 'Female', 1, 'A'),
                                                                                                                                        (4, 12, 12, '25-50', 'Both', 2, 'R'),
                                                                                                                                        (1, 13, 13, '18-35', 'Male', 3, 'P'),
                                                                                                                                        (4, 14, 14, '25-50', 'Female', 4, 'A'),
                                                                                                                                        (1, 15, 15, '18-35', 'Both', 5, 'R'),
                                                                                                                                        (4, 16, 16, '25-50', 'Male', 1, 'P'),
                                                                                                                                        (1, 17, 17, '18-35', 'Female', 2, 'A'),
                                                                                                                                        (4, 18, 18, '25-50', 'Both', 3, 'R'),
                                                                                                                                        (1, 19, 19, '18-35', 'Male', 4, 'P'),
                                                                                                                                        (4, 20, 20, '25-50', 'Female', 5, 'A');
