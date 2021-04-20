CREATE TABLE occupations (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	description text NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	CONSTRAINT occupations_pkey PRIMARY KEY (id)
);

CREATE TABLE users (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	login varchar(100) NOT NULL,
	"password" varchar(100) NOT NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE wings (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	CONSTRAINT wings_pkey PRIMARY KEY (id)
);
