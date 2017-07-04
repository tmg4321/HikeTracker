-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hiketrackdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `hiketrackdb` ;

-- -----------------------------------------------------
-- Schema hiketrackdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hiketrackdb` DEFAULT CHARACTER SET utf8 ;
USE `hiketrackdb` ;

-- -----------------------------------------------------
-- Table `hike`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hike` ;

CREATE TABLE IF NOT EXISTS `hike` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT 'unnamed',
  `distance` DECIMAL(5,2) NULL,
  `elevation` INT NULL,
  `time` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `picture`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `picture` ;

CREATE TABLE IF NOT EXISTS `picture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hike_id` INT NOT NULL,
  `url` VARCHAR(500) NOT NULL DEFAULT 'http://s3.amazonaws.com/uploads.knightlab.com/storymapjs/1153aa6361defd2d8dc7de6401affc6f/appalachian-trail/_images/ATC_RP1721_4550254473_7ac1b0c900_b_Dave%20Pidgeon.jpg',
  PRIMARY KEY (`id`),
  INDEX `fk_picture_hike_idx` (`hike_id` ASC),
  CONSTRAINT `fk_picture_hike`
    FOREIGN KEY (`hike_id`)
    REFERENCES `hike` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
GRANT USAGE ON *.* TO user@localhost;
 DROP USER user@localhost;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'user'@'localhost' IDENTIFIED BY 'user';

GRANT ALL ON * TO 'user'@'localhost';
GRANT SELECT, INSERT, TRIGGER ON TABLE * TO 'user'@'localhost';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'user'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `hike`
-- -----------------------------------------------------
START TRANSACTION;
USE `hiketrackdb`;
INSERT INTO `hike` (`id`, `name`, `distance`, `elevation`, `time`) VALUES (DEFAULT, 'Bluffs Regional Park', 2.4, 200, 65);
INSERT INTO `hike` (`id`, `name`, `distance`, `elevation`, `time`) VALUES (DEFAULT, 'South Valley Park Trail', 3.0, 500, 80);
INSERT INTO `hike` (`id`, `name`, `distance`, `elevation`, `time`) VALUES (DEFAULT, 'Mills Lake', 5.3, 1000, 140);
INSERT INTO `hike` (`id`, `name`, `distance`, `elevation`, `time`) VALUES (DEFAULT, 'Ouzel Falls', 5.4, 900, 125);

COMMIT;


-- -----------------------------------------------------
-- Data for table `picture`
-- -----------------------------------------------------
START TRANSACTION;
USE `hiketrackdb`;
INSERT INTO `picture` (`id`, `hike_id`, `url`) VALUES (DEFAULT, 1, 'http://dayhikesneardenver.com/wp-content/uploads/2010/08/01-bluffs-loop-trail-header.jpg');
INSERT INTO `picture` (`id`, `hike_id`, `url`) VALUES (DEFAULT, 2, 'https://wanderingcolorado.files.wordpress.com/2013/03/bench-on-swallow-trail-at-junction-with-trail-to-grazing-elk-trail.jpg');
INSERT INTO `picture` (`id`, `hike_id`, `url`) VALUES (DEFAULT, 3, 'http://www.protrails.com/protrails/trails/RMNP%20-%20Mills%20Lake%201.jpg');
INSERT INTO `picture` (`id`, `hike_id`, `url`) VALUES (DEFAULT, 4, 'http://www.rockymountainhikingtrails.com/rocky-mountain-photos/bluebird-lake/upper-copeland-falls-rmnp.jpg');

COMMIT;
