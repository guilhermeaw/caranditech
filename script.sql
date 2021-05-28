CREATE TABLE cells (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	ref_wing int4 NOT NULL,
	CONSTRAINT cells_pkey PRIMARY KEY (id)
);


-- public.cells foreign keys

ALTER TABLE public.cells ADD CONSTRAINT cells_fk_wing_id FOREIGN KEY (ref_wing) REFERENCES wings(id) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE employees (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	cpf varchar(11) NOT NULL,
	phone varchar(11) NOT NULL,
	ref_occupation int4 NOT NULL,
	ref_wing int4 NOT NULL,
	CONSTRAINT employees_pkey PRIMARY KEY (id)
);


-- public.employees foreign keys

ALTER TABLE public.employees ADD CONSTRAINT employees_fk_occupation_id FOREIGN KEY (ref_occupation) REFERENCES occupations(id) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE occupations (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	description text NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	wage numeric(2) NOT NULL DEFAULT 0.00,
	CONSTRAINT occupations_pkey PRIMARY KEY (id)
);

CREATE TABLE occupations_employees_mappings (
	ref_occupation int4 NOT NULL,
	ref_employee int4 NOT NULL,
	start_date date NOT NULL,
	end_date date NULL,
	CONSTRAINT occupations_employees_mappings_pk PRIMARY KEY (ref_occupation, ref_employee)
);


-- public.occupations_employees_mappings foreign keys

ALTER TABLE public.occupations_employees_mappings ADD CONSTRAINT occupations_employees_mappings_fk_employee_id FOREIGN KEY (ref_employee) REFERENCES employees(id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE public.occupations_employees_mappings ADD CONSTRAINT occupations_employees_mappings_fk_occupation_id FOREIGN KEY (ref_occupation) REFERENCES occupations(id) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE occurrences (
	id serial NOT NULL,
	title varchar(100) NOT NULL,
	description text NULL,
	state int2 NOT NULL,
	ref_prisoner int4 NOT NULL,
	ref_user int4 NOT NULL,
	created_date timestamp NOT NULL,
	CONSTRAINT occurrences_pkey PRIMARY KEY (id)
);


-- public.occurrences foreign keys

ALTER TABLE public.occurrences ADD CONSTRAINT occurrences_fk_prisoner_id FOREIGN KEY (ref_prisoner) REFERENCES prisoners(id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE public.occurrences ADD CONSTRAINT occurrences_fk_user_id FOREIGN KEY (ref_user) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE prisoner_types (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	profit numeric(2) NOT NULL DEFAULT 0.00,
	description text NULL,
	state int2 NOT NULL,
	CONSTRAINT prisoner_types_pkey PRIMARY KEY (id)
);

CREATE TABLE prisoners (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	ref_cell int4 NOT NULL,
	ref_prisoner_type int4 NOT NULL,
	enter_date date NOT NULL,
	exit_date date NULL,
	CONSTRAINT prisoners_pkey PRIMARY KEY (id)
);


-- public.prisoners foreign keys

ALTER TABLE public.prisoners ADD CONSTRAINT prisoners_fk_cell_id FOREIGN KEY (ref_cell) REFERENCES cells(id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE public.prisoners ADD CONSTRAINT prisoners_fk_prisoner_type_id FOREIGN KEY (ref_prisoner_type) REFERENCES prisoner_types(id) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE users (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	login varchar(100) NOT NULL,
	"password" varchar(100) NOT NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE visitors (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	cpf varchar(11) NOT NULL,
	phone varchar(11) NOT NULL,
	state int2 NOT NULL,
	CONSTRAINT visitors_pkey PRIMARY KEY (id)
);

CREATE TABLE visits (
	id serial NOT NULL,
	schedule_date timestamp NOT NULL,
	state int2 NOT NULL,
	ref_prisoner int4 NOT NULL,
	ref_visitor int4 NOT NULL,
	CONSTRAINT visits_pkey PRIMARY KEY (id)
);


-- public.visits foreign keys

ALTER TABLE public.visits ADD CONSTRAINT visits_fk_prisoner_id FOREIGN KEY (ref_prisoner) REFERENCES prisoners(id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE public.visits ADD CONSTRAINT visits_fk_visitor_id FOREIGN KEY (ref_visitor) REFERENCES visitors(id) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE wings (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	state int2 NOT NULL DEFAULT '0'::smallint,
	CONSTRAINT wings_pkey PRIMARY KEY (id)
);
