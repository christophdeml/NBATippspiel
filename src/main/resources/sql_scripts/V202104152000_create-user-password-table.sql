create table user_password
(
	password varchar(320) not null,
	user_id int not null,
	constraint user_id_UNIQUE
		unique (user_id),
	constraint fk_user_password_userid
		foreign key (user_id) references user (id)
);

alter table user_password
	add primary key (user_id);

