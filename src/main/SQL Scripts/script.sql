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

CREATE TABLE advertisement (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               advertisement_url VARCHAR(255) NOT NULL
);

CREATE TABLE UserAdvertisement (
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

-- Assuming your table name is 'advertisement_table'
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
                                                                                                                      (4, 1, '26-35',  4, 'S', 'Female'),
                                                                                                                      (1, 1, '36-45',  1, 'A', 'Both'),
                                                                                                                      (4, 1, '18-25',  4, 'R', 'Male'),
                                                                                                                      (1, 1, '26-35',  1, 'S', 'Female'),
                                                                                                                      (4, 1, '36-45',  4, 'A', 'Both'),
                                                                                                                      (1, 1, '18-25', 6, 'R', 'Male'),
                                                                                                                      (4, 1, '26-35',  4, 'S', 'Female'),
                                                                                                                      (1, 1, '36-45',  1, 'A', 'Both'),
                                                                                                                      (4, 1, '18-25',  4, 'R', 'Male'),
                                                                                                                      (1, 1, '26-35',  1, 'S', 'Female'),
                                                                                                                      (4, 1, '36-45', 4, 'A', 'Both'),
                                                                                                                      (1, 1, '18-25', 7,'R', 'Male'),
                                                                                                                      (4, 1, '26-35',  4, 'S', 'Female'),
                                                                                                                      (1, 1, '36-45',  4,'A', 'Both'),
                                                                                                                      (4, 1, '18-25',  4, 'R', 'Male'),
                                                                                                                      (1, 1, '26-35',  1, 'S', 'Female'),
                                                                                                                      (4, 1, '36-45',  4, 'A', 'Both'),
                                                                                                                      (1, 1, '18-25', 5, 'R', 'Male'),
                                                                                                                      (4, 1, '26-35',  4, 'S', 'Female');
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
                                                                     (4, 'Payment issue', 'RS'),
                                                                     (1, 'Profile update error', 'P'),
                                                                     (4, 'App crashing', 'RS');

