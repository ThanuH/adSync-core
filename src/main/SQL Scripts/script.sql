use adsyncdb;

-- Create advertisement table & insert Data
create table advertisement
(
    id                int auto_increment
        primary key,
    advertisement_url varchar(255) not null
);

INSERT INTO advertisement (advertisement_url) VALUES
                                                  ('https://example.com/ad1'),
                                                  ('https://example.com/ad2'),
                                                  ('https://example.com/ad3'),
                                                  ('https://example.com/ad4'),
                                                  ('https://example.com/ad5');

-- ---------------------------------------------------------------------------------------------------------------------

-- Create business_category table & insert Data
create table business_category
(
    id            int auto_increment
        primary key,
    category_name varchar(255) not null
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

-- ---------------------------------------------------------------------------------------------------------------------

-- Create user_role table & insert Data
create table user_role
(
    id               int auto_increment
        primary key,
    role_description varchar(255) not null
);

INSERT INTO user_role (role_description) VALUES ('admin');
INSERT INTO user_role (role_description) VALUES ('user');

-- ---------------------------------------------------------------------------------------------------------------------

-- Create user table & insert Data
create table user
(
    id                           int auto_increment
        primary key,
    contact_number               varchar(20)  not null,
    email                        varchar(100) not null,
    business_registration_number varchar(100) null,
    business_name                varchar(255) null,
    business_category_id         int          null,
    password                     varchar(255) not null,
    status                       varchar(20)  null,
    user_role_id                 int          null,
    constraint business_registration_number
        unique (business_registration_number),
    constraint email
        unique (email),
    constraint fk_user_role
        foreign key (user_role_id) references user_role (id),
    constraint user_ibfk_1
        foreign key (business_category_id) references business_category (id)
);

create index business_category_id
    on user (business_category_id);

-- Inserting data into the user table with status as either 'Active', 'Inactive', or 'Blocked'
INSERT INTO user (contact_number, email, business_registration_number, business_name, business_category_id, password, status, user_role_id)
VALUES
    ('1234567890', 'user1@example.com', 'BRN001', 'Business1', 1, 'password1', 'A', 2),
    ('9876543210', 'user2@example.com', 'BRN002', 'Business2', 2, 'password2', 'In', 2),
    ('5678901234', 'user3@example.com', 'BRN003', 'Business3', 3, 'password3', 'B', 2),
    ('4567890123', 'user4@example.com', 'BRN004', 'Business4', 4, 'password4', 'A', 2),
    ('3456789012', 'user5@example.com', 'BRN005', 'Business5', 5, 'password5', 'In', 2),
    ('2345678901', 'user6@example.com', 'BRN006', 'Business6', 6, 'password6', 'B', 2),
    ('7890123456', 'user7@example.com', 'BRN007', 'Business7', 7, 'password7', 'A', 2),
    ('8901234567', 'user8@example.com', 'BRN008', 'Business8', 8, 'password8', 'In', 2),
    ('9012345678', 'user9@example.com', 'BRN009', 'Business9', 9, 'password9', 'B', 2),
    ('1122334455', 'user10@example.com', 'BRN010', 'Business10', 10, 'password10', 'In', 2);


-- ---------------------------------------------------------------------------------------------------------------------

-- Create business_category table & insert Data
create table user_advertisement
(
    id                   int auto_increment
        primary key,
    user_id              int          null,
    priority             int          null,
    targeted_age         varchar(50)  null,
    targeted_audience    varchar(255) null,
    business_category_id int          null,
    status               varchar(2)   not null,
    constraint user_advertisement_ibfk_1
        foreign key (user_id) references user (id),
    constraint user_advertisement_ibfk_2
        foreign key (business_category_id) references business_category (id)
);


-- Inserting data into the user_advertisement table
INSERT INTO user_advertisement (user_id, priority, targeted_age, targeted_audience, business_category_id, status)
VALUES
    (1, 1, '18-25', 'Male', 1, 'R'),
    (2, 2, '26-35', 'Female', 2, 'S'),
    (3, 3, '36-45', 'Both', 3, 'A'),
    (4, 1, '18-25', 'Male', 4, 'R'),
    (5, 2, '26-35', 'Female', 5, 'S'),
    (6, 3, '36-45', 'Both', 6, 'A'),
    (7, 1, '18-25', 'Male', 7, 'R'),
    (8, 2, '26-35', 'Female', 8, 'S'),
    (9, 3, '36-45', 'Both', 9, 'A'),
    (10, 1, '18-25', 'Male', 10, 'R'),
    (1, 2, '26-35', 'Female', 11, 'S'),
    (2, 3, '36-45', 'Both', 12, 'A'),
    (3, 1, '18-25', 'Male', 13, 'R'),
    (4, 2, '26-35', 'Female', 14, 'S'),
    (5, 3, '36-45', 'Both', 15, 'A'),
    (6, 1, '18-25', 'Male', 16, 'R'),
    (7, 2, '26-35', 'Female', 17, 'S'),
    (8, 3, '36-45', 'Both', 18, 'A'),
    (9, 1, '18-25', 'Male', 19, 'R'),
    (10, 2, '26-35', 'Female', 20, 'S');

create index business_category_id
    on user_advertisement (business_category_id);

create index user_id
    on user_advertisement (user_id);

-- ---------------------------------------------------------------------------------------------------------------------

-- Create business_category table & insert Data
create table reported_issues
(
    id                int auto_increment
        primary key,
    user_id           int          null,
    issue_description varchar(255) not null,
    status            varchar(2)   not null,
    constraint reported_issues_ibfk_1
        foreign key (user_id) references user (id)
);

create index user_id
    on reported_issues (user_id);

-- Inserting data into the reported_issues table
INSERT INTO reported_issues (user_id, issue_description, status)
VALUES
    (1, 'Login issue', 'P'),
    (2, 'Payment problem', 'R'),
    (3, 'Profile update error', 'P'),
    (4, 'App crashing', 'R'),
    (5, 'Password reset issue', 'P'),
    (6, 'Billing discrepancy', 'R'),
    (7, 'Feature request', 'P'),
    (8, 'Account suspension', 'R'),
    (9, 'Email not receiving', 'P'),
    (10, 'Bug report', 'R'),
    (1, 'Forgot password', 'P'),
    (2, 'Transaction history error', 'R'),
    (3, 'Security concern', 'P'),
    (4, 'Device compatibility', 'R'),
    (5, 'Feedback submission', 'P'),
    (6, 'Subscription cancellation', 'R'),
    (7, 'Notification settings', 'P'),
    (8, 'Refund request', 'R'),
    (9, 'Website not loading', 'P'),
    (10, 'Account deletion request', 'R');


-- ---------------------------------------------------------------------------------------------------------------------



