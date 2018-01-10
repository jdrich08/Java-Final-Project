CREATE database moviedb;

USE moviedb;

CREATE TABLE movie (
	Movie_ID int NOT NULL,
    Movie_Name varchar(45) NOT NULL,
    Movie_Description varchar(255) NOT NULL,
    PRIMARY KEY (Movie_ID)
);

CREATE TABLE rating (
    Rating_ID int NOT NULL,
    Rating_Comment varchar(255),
    Rating_Num int NOT NULL,
    Movie_ID int NOT NULL,
    PRIMARY KEY (Rating_ID),
    FOREIGN KEY (Movie_ID) REFERENCES movie(Movie_ID)
);




