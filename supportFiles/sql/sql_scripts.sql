// Create schema
CREATE SCHEMA `tictactoe_schema` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

// Create table User
CREATE TABLE `tictactoe_schema`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(60) NULL,
  `stats_wins` INT NULL,
  `stats_loses` INT NULL,
  `stats_tied` INT NULL,
  `last_date_played` VARCHAR(10) NULL,
  PRIMARY KEY (`iduser`));
  

// Create table Games
CREATE TABLE `tictactoe_schema`.`games` (
  `idgames` INT NOT NULL AUTO_INCREMENT,
  `player_x` INT NULL,
  `player_y` INT NULL,
  `won_x_or_y` VARCHAR(1) NULL,
  PRIMARY KEY (`idgames`));
  
  
// Create table Play
CREATE TABLE `tictactoe_schema`.`play` (
  `playid` INT NOT NULL AUTO_INCREMENT,
  `game_id` INT NULL,
  `position` INT NULL,
  PRIMARY KEY (`playid`));
  

// Foreign Keys
// fk_games
ALTER TABLE `tictactoe_schema`.`play` 
ADD INDEX `fk_games_idx` (`game_id` ASC);
ALTER TABLE `tictactoe_schema`.`play` 
ADD CONSTRAINT `fk_games`
  FOREIGN KEY (`game_id`)
  REFERENCES `tictactoe_schema`.`games` (`idgames`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
// fk_user
ALTER TABLE `tictactoe_schema`.`games` 
ADD INDEX `fk_user_x_idx` (`player_x` ASC),
ADD INDEX `fk_user_y_idx` (`player_y` ASC);
ALTER TABLE `tictactoe_schema`.`games` 
ADD CONSTRAINT `fk_user_x`
  FOREIGN KEY (`player_x`)
  REFERENCES `tictactoe_schema`.`user` (`iduser`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_user_y`
  FOREIGN KEY (`player_y`)
  REFERENCES `tictactoe_schema`.`user` (`iduser`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
