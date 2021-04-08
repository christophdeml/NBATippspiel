create table user
(
	id int not null,
	username varchar(25) not null,
	firstname varchar(50) null,
	lastname varchar(50) null,
	points int default 0 null,
	constraint user_id_uindex
		unique (id),
	constraint user_username_uindex
		unique (username)
);

alter table user
	add primary key (id);

