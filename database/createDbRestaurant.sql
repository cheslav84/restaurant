-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema restaurant
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema restaurant
-- -----------------------------------------------------
-- CREATE SCHEMA IF NOT EXISTS `restaurant` DEFAULT CHARACTER SET utf8 ;
USE `restaurant` ;

-- -----------------------------------------------------
-- Table `restaurant`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`category` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_category_name` (`name` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));



-- -----------------------------------------------------
-- Table `restaurant`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`dish` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `weight` INT UNSIGNED NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  `amount` INT UNSIGNED NOT NULL,
  `special` TINYINT(1) NULL DEFAULT 0,
  `image` VARCHAR(45) NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT INDEX `idx_dish_name` (`name`),
  INDEX `idx_dish_price` (`price` ASC, `name` ASC),
  INDEX `fk_dish_category_idx` (`category_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  CONSTRAINT `fk_dish_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `restaurant`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `restaurant`.`booking_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`booking_status` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`booking_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));


-- -----------------------------------------------------
-- Table `restaurant`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`role` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));


-- -----------------------------------------------------
-- Table `restaurant`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`user` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(32) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `name` VARCHAR(24) NOT NULL,
  `surname` VARCHAR(24) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role1_idx` (`role_id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `restaurant`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `restaurant`.`booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`booking` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`booking` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(1024) NOT NULL,
  `phone_number` VARCHAR(13) NOT NULL,
  `payment` TINYINT(1) NULL DEFAULT 0,
  `creation_data` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `close_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_booking_booking_status_idx` (`status_id` ASC),
  INDEX `fk_booking_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_booking_booking_status`
    FOREIGN KEY (`status_id`)
    REFERENCES `restaurant`.`booking_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `restaurant`.`booking_has_dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`booking_has_dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`booking_has_dish` (
  `booking_id` INT NOT NULL,
  `dish_id` INT NOT NULL,
  `amount` INT UNSIGNED NOT NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`booking_id`, `dish_id`),
  INDEX `fk_booking_has_dish_dish1_idx` (`dish_id` ASC),
  INDEX `fk_booking_has_dish_booking1_idx` (`booking_id` ASC),
  CONSTRAINT `fk_booking_has_dish_booking1`
    FOREIGN KEY (`booking_id`)
    REFERENCES `restaurant`.`booking` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_has_dish_dish1`
    FOREIGN KEY (`dish_id`)
    REFERENCES `restaurant`.`dish` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `restaurant`.`user_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`user_details` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`user_details` (
  `user_id` INT NOT NULL,
  `birthdate` DATE NULL,
  `passport` VARCHAR(16) NULL,
  `bank_account` VARCHAR(29) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `passport_UNIQUE` (`passport` ASC),
  UNIQUE INDEX `bank_account_UNIQUE` (`bank_account` ASC),
  CONSTRAINT `fk_user_details_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
