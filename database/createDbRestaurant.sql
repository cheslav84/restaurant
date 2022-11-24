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
  `name` VARCHAR(45) NOT NULL,
  `parent` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_category_category_idx` (`parent` ASC),
  INDEX `idx_category_name` (`name` ASC, `parent` ASC),
  CONSTRAINT `fk_category_category1`
    FOREIGN KEY (`parent`)
    REFERENCES `restaurant`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `restaurant`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`category` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `parent` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_category_category_idx` (`parent` ASC),
  INDEX `idx_category_name` (`name` ASC, `parent` ASC),
  CONSTRAINT `fk_category_category1`
    FOREIGN KEY (`parent`)
    REFERENCES `restaurant`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `restaurant`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`dish` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  `amount` INT UNSIGNED NOT NULL,
  `image` VARCHAR(45) NULL,
  `dish_detail` VARCHAR(64) NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT INDEX `idx_dish_name` (`name`),
  INDEX `idx_dish_price` (`price` ASC, `name` ASC),
  INDEX `fk_dish_category_idx` (`category_id` ASC),
  CONSTRAINT `fk_dish_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `restaurant`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `restaurant`.`commition_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`commition_status` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`commition_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `restaurant`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`role` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`id`));


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
-- Table `restaurant`.`commition`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`commition` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`commition` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(1024) NOT NULL,
  `phone_number` VARCHAR(12) NOT NULL,
  `creation_data` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `close_date` TIMESTAMP NULL,
  `status_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`, `status_id`),
  INDEX `fk_commition_commition_status1_idx` (`status_id` ASC),
  INDEX `fk_commition_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_commition_commition_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `restaurant`.`commition_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_commition_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `restaurant`.`commition_has_dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`commition_has_dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`commition_has_dish` (
  `commition_id` INT NOT NULL,
  `dish_id` INT NOT NULL,
  `amount` INT UNSIGNED NOT NULL,
  `price` DECIMAL(9,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`commition_id`, `dish_id`),
  INDEX `fk_commition_has_dish_dish1_idx` (`dish_id` ASC),
  INDEX `fk_commition_has_dish_commition1_idx` (`commition_id` ASC),
  CONSTRAINT `fk_commition_has_dish_commition1`
    FOREIGN KEY (`commition_id`)
    REFERENCES `restaurant`.`commition` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_commition_has_dish_dish1`
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
  `birthday` DATE NULL,
  `passport` VARCHAR(16) NULL,
  `bank_account` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_details_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `restaurant`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `restaurant`.`menu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`menu` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`menu` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `restaurant`.`menu_has_dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `restaurant`.`menu_has_dish` ;

CREATE TABLE IF NOT EXISTS `restaurant`.`menu_has_dish` (
  `menu_id` INT NOT NULL,
  `dish_id` INT NOT NULL,
  PRIMARY KEY (`menu_id`, `dish_id`),
  INDEX `fk_menu_has_dish_dish1_idx` (`dish_id` ASC),
  INDEX `fk_menu_has_dish_menu1_idx` (`menu_id` ASC),
  CONSTRAINT `fk_menu_has_dish_menu1`
    FOREIGN KEY (`menu_id`)
    REFERENCES `restaurant`.`menu` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_menu_has_dish_dish1`
    FOREIGN KEY (`dish_id`)
    REFERENCES `restaurant`.`dish` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
