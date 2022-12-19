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
  `category_name` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_category_name` (`category_name` ASC),
  UNIQUE INDEX `name_UNIQUE` (`category_name` ASC));


-- -----------------------------------------------------
-- Table `restaurant`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`dish` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dish_name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `weight` INT UNSIGNED NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  `amount` INT UNSIGNED NOT NULL,
  `spirits` TINYINT(1) NOT NULL,
  `image` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  FULLTEXT INDEX `idx_dish_name` (`dish_name`),
  INDEX `idx_dish_price` (`price` ASC, `dish_name` ASC),
  UNIQUE INDEX `name_UNIQUE` (`dish_name` ASC) );


-- -----------------------------------------------------
-- Table `restaurant`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`category` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_category_name` (`category_name` ASC),
  UNIQUE INDEX `name_UNIQUE` (`category_name` ASC));


-- -----------------------------------------------------
-- Table `restaurant`.`booking_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`booking_status` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`booking_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `booking_status_name` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`booking_status_name` ASC));


-- -----------------------------------------------------
-- Table `restaurant`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`role` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`role_name` ASC));


-- -----------------------------------------------------
-- Table `restaurant`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`user` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(32) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `user_name` VARCHAR(24) NOT NULL,
  `surname` VARCHAR(24) NOT NULL,
  `gender` VARCHAR(8) NOT NULL,
  `age_over_eighteen` TINYINT(1) UNSIGNED NOT NULL,
  `creation_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role_idx` (`role_id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `restaurant`.`role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `restaurant`.`custom_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`custom_order` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`custom_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(1024) NOT NULL,
  `phone_number` VARCHAR(13) NOT NULL,
  `payment` TINYINT(1) NULL DEFAULT 0,
  `creation_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `close_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` INT NOT NULL,
  `booking_status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_custom_order_booking_status_idx` (`booking_status_id` ASC),
  INDEX `fk_custom_order_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_custom_order_booking_status`
    FOREIGN KEY (`booking_status_id`)
    REFERENCES `restaurant`.`booking_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_custom_order_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `restaurant`.`basket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`basket` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`basket` (
  `custom_order_id` INT NOT NULL,
  `dish_id` INT NOT NULL,
  `amount` INT UNSIGNED NOT NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`custom_order_id`, `dish_id`),
  INDEX `fk_basket_dish_idx` (`dish_id` ASC),
  INDEX `fk_basket_custom_order_idx` (`custom_order_id` ASC),
  CONSTRAINT `fk_basket_custom_order`
    FOREIGN KEY (`custom_order_id`)
    REFERENCES `restaurant`.`custom_order` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_basket_dish`
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
  `birthdate` DATE NOT NULL,
  `passport` VARCHAR(16) NOT NULL,
  `bank_account` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `passport_UNIQUE` (`passport` ASC),
  UNIQUE INDEX `bank_account_UNIQUE` (`bank_account` ASC),
  CONSTRAINT `fk_user_details_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `restaurant`.`dish_has_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`dish_has_category` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`dish_has_category` (
  `dish_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`dish_id`, `category_id`),
  INDEX `fk_dish_has_category_category_idx` (`category_id` ASC),
  INDEX `fk_dish_has_category_dish_idx` (`dish_id` ASC),
  CONSTRAINT `fk_dish_has_category_dish`
    FOREIGN KEY (`dish_id`)
    REFERENCES `restaurant`.`dish` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_dish_has_category_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `restaurant`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
