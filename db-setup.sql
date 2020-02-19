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

insert into person (id, legal_name, username, password)
	values (1, 'Brian Gottreu', 'gottreu', crypt('security', gen_salt('bf'))),
	(2, 'Susan Jones', 'jones', crypt('fancypass', gen_salt('bf')));

--select crypt('security', (select password from person where username='gottreu')) = password from person where username = 'gottreu';
create table account (
	id serial primary key,
	description text
);

insert into account (id, description) values
	(1, 'Checking account'),
	(2, 'Checking account'),
	(3, 'Savings account');

create table ownership (
	id serial primary key,
	person_id int references person(id),
	account_id int references account(id)
);

insert into ownership (person_id, account_id) values
	(1,1), -- gottreu checking
	(2,2), -- jones checking
	(2,3); -- jones savings

select a.id, description from account a join ownership o on a.id = o.account_id 
	join person p on o.person_id = p.id
	where p.username = 'gottreu';

create table transaction (
	id serial primary key,
	account_id int references account(id),
	tstamp timestamp,
	amount numeric(20,2),
	description text
);

/*insert into "transaction" (account_id, tstamp, amount, description) values
	(1, '1/1/2020 12:13PM', 1000, 'Account opening'),
	(1, '1/2/2020 6:35AM', -11.74, 'Denny''s'),
	(1, '1/2/2020 12:15PM', -7.83, 'Taco Bell'),
	(1, '1/2/2020 5:45PM', -35.14, 'Cheesecake Factory');
	*/

INSERT INTO public.transaction VALUES (1, 1, '2020-01-01 12:13:00', 1000.00, 'Account opening');
INSERT INTO public.transaction VALUES (2, 1, '2020-01-02 06:35:00', -11.74, 'Denny''s');
INSERT INTO public.transaction VALUES (3, 1, '2020-01-02 12:15:00', -7.83, 'Taco Bell');
INSERT INTO public.transaction VALUES (4, 1, '2020-01-02 17:45:00', -35.14, 'Cheesecake Factory');
INSERT INTO public.transaction VALUES (5, 1, '2020-02-18 21:45:53.624', 20.00, 'Deposit');
INSERT INTO public.transaction VALUES (6, 1, '2020-02-18 21:47:05.981', 100.00, 'Deposit');
INSERT INTO public.transaction VALUES (7, 2, '2020-02-18 22:17:39.12', 25000.00, 'Deposit');
INSERT INTO public.transaction VALUES (8, 2, '2020-02-18 22:17:45.363', -100.00, 'Withdrawal');
INSERT INTO public.transaction VALUES (9, 2, '2020-02-18 22:17:51.155', -100.00, 'Withdrawal');
INSERT INTO public.transaction VALUES (10, 2, '2020-02-18 22:18:07.227', -24800.00, 'Withdrawal');
INSERT INTO public.transaction VALUES (11, 2, '2020-02-18 22:18:22.322', 55000.00, 'Deposit');

select sum(amount) from "transaction" t
	where account_id = 1;

