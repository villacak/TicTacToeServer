-- Create schema
CREATE SCHEMA `tictactoe_schema` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

-- Create user table
CREATE TABLE user
(
    iduser INT(11) PRIMARY KEY NOT NULL,
    user_name VARCHAR(60),
    stats_wins INT(11),
    stats_loses INT(11),
    stats_tied INT(11),
    last_date_played VARCHAR(10)
);

-- Create games table
CREATE TABLE games
(
    idgames INT(11) PRIMARY KEY NOT NULL,
    player_x_or_o VARCHAR(1),
    player INT(11),
    won_x_or_y VARCHAR(1),
    game INT(11),
    players_number INT(11),
    CONSTRAINT fk_user FOREIGN KEY (player) REFERENCES user (iduser)
);
CREATE INDEX game_idx ON games (game);
CREATE INDEX player_idx ON games (player);


-- Create play table
CREATE TABLE play
(
    playid INT(11) PRIMARY KEY NOT NULL,
    game INT(11),
    position INT(11),
    CONSTRAINT fk_games FOREIGN KEY (game) REFERENCES games (idgames)
);
CREATE INDEX fk_games_idx ON play (game);
