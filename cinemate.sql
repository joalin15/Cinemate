DROP SCHEMA IF EXISTS `cinemate`;
CREATE SCHEMA `cinemate` ;

CREATE TABLE `cinemate`.`user` (
  `user_username` VARCHAR(45) BINARY NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `user_fname` VARCHAR(45) NOT NULL,
  `user_lname` VARCHAR(45) NOT NULL,
  `user_image` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_username`));

CREATE TABLE `cinemate`.`movie` (
  `movie_id` VARCHAR(20) NOT NULL,
  `movie_title` VARCHAR(45) NOT NULL,
  `movie_total_ratings` INT NULL,
  `movie_rating_total` VARCHAR(45) NULL,
  PRIMARY KEY (`movie_id`));
  
  CREATE TABLE `cinemate`.`event` (
  `event_id` INT NOT NULL AUTO_INCREMENT,
  `event_username` VARCHAR(45) BINARY NOT NULL,
  `event_action` VARCHAR(45) NOT NULL,
  `event_rating` INT NULL,
  `event_movie_id` VARCHAR(20) NOT NULL,
  `event_timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`event_id`),
  INDEX `event_movie_id_idx` (`event_movie_id` ASC),
  INDEX `event_username_idx` (`event_username` ASC),
  CONSTRAINT `event_movie_id`
    FOREIGN KEY (`event_movie_id`)
    REFERENCES `cinemate`.`movie` (`movie_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `event_username`
    FOREIGN KEY (`event_username`)
    REFERENCES `cinemate`.`user` (`user_username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `cinemate`.`follow` (
	follow_username_following VARCHAR(45) NOT NULL, 
	follow_username_being_followed VARCHAR(45) NOT NULL,
	PRIMARY KEY (follow_username_following, follow_username_being_followed)
);


