drop table if exists transaction;
drop table if exists ownership;
drop table if exists account;
drop table if exists person;


create table person (
	id serial primary key,
	legal_name text,
	username text unique,
	password text
);

--create extension pgcrypto;

insert into person (legal_name, username, password)
	values ('Brian Gottreu', 'gottreu', crypt('security', gen_salt('bf'))),
	('Susan Jones', 'jones', crypt('fancypass', gen_salt('bf')));

create table account (
	id serial primary key,
	description text
);

create table ownership (
	id serial primary key,
	person_id int references person(id),
	account_id int references account(id)
);

create table transaction (
	id serial primary key,
	account_id int references account(id),
	tstamp timestamp,
	amount numeric(20,2),
	description text
);
