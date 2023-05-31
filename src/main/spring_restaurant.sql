CREATE DATABASE  IF NOT EXISTS spring_restaurant;
USE spring_restaurant;

DROP TABLE IF EXISTS phone_number;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS store_hours;
DROP TABLE IF EXISTS phone_number;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS spring_session_attributes;
DROP TABLE IF EXISTS spring_session;

-- Users for a restaurant.
CREATE TABLE `user` (
  user_id bigint NOT NULL AUTO_INCREMENT,
  username varchar(128) NOT NULL UNIQUE,
  `password` varchar(256) NOT NULL,
  permission varchar(32) NOT NULL,
  first_name varchar(64) NOT NULL,
  last_name varchar(64) NOT NULL,
  first_login boolean NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT unique_name UNIQUE (first_name, last_name)
);

-- Single hard-coded manager. No other managers should be addded.
-- ManagerPass is the password
INSERT INTO `user` VALUES (0,'Manager','$2b$10$p7OgAcPH4iHbXDjOmYJ/ieWOH/8pkMsgewF3TYbPM/11o70RK9lq2','Manager','Restaurant','Manager', false);
-- Dummy Employee.
-- EmployeePass is the password
INSERT INTO `user` VALUES (0,'Employee','$2b$10$yMWDtddYdrhQTuzif7Qqh.ymwkJUbQIHKAj3Bl0M.57rcED.M/eeu','Employee','Restaurant','Employee', true);

-- The hours of a restaurant for a particular day.
CREATE TABLE store_hours (
  hours_id bigint NOT NULL AUTO_INCREMENT,
  -- Days are numbered 0-M, 1-T, 2-W, 3-Th, 4-F, 5-Sa, 6-Su.
  day_of_week tinyint NOT NULL CHECK(day_of_week >= 0 and day_of_week <= 6) UNIQUE,
  `open` time(0) NOT NULL,
  `close` time(0) NOT NULL,
  PRIMARY KEY (hours_id)
);

-- Need to have api only update and never insert.
INSERT INTO `store_hours` VALUES (0, 0, "8:00:00", "20:00:00");
INSERT INTO `store_hours` VALUES (0, 1, "8:00:00", "20:00:00");
INSERT INTO `store_hours` VALUES (0, 2, "8:00:00", "20:00:00");
INSERT INTO `store_hours` VALUES (0, 3, "8:00:00", "20:00:00");
INSERT INTO `store_hours` VALUES (0, 4, "8:00:00", "20:00:00");
INSERT INTO `store_hours` VALUES (0, 5, "8:00:00", "20:00:00");
INSERT INTO `store_hours` VALUES (0, 6, "8:00:00", "20:00:00");

-- A menu item.
CREATE TABLE item (
  item_id bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  price double NOT NULL,
  photo_location text NOT NULL,
  -- Availability of food item: 0 - unavailable, 1 - limited availability, 2 - available.
  availability tinyint NOT NULL,
  PRIMARY KEY (item_id)
);

-- A contact phone number.
CREATE TABLE phone_number (
  phone_id bigint NOT NULL AUTO_INCREMENT,
  contact_name varchar(128) NOT NULL,
  `number` varchar(24) NOT NULL,
  PRIMARY KEY (phone_id)
);

INSERT INTO phone_number VALUES (0, "Jeff Bridges", 6083936708);
INSERT INTO phone_number VALUES (0, "The Dude", 6084874984);