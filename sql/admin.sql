
create table dict(
	id int IDENTITY(1,1) NOT NULL,
	dict_type varchar(50),
	dict_key varchar(50),
	dict_value varchar(200)
)

CREATE TABLE admin(
	id int IDENTITY(1,1) NOT NULL,
	name varchar(100),
	password varchar(100)
)

drop table if exists currency;
CREATE TABLE currency(
	id int IDENTITY(1,1) NOT NULL,
	name varchar(100),
	code varchar(100),
	rate numeric,
	icon_class varchar(25)
)

create table oa_static_info(
   id int IDENTITY(1,1) NOT NULL,
   export_path varchar(500)
)

create table tntxiaoa_design_stamp(
	id int IDENTITY(1,1) NOT NULL,
	app_path varchar(100),
	real_path varchar(300),
	app varchar(100),
	width int,
	height int,
	create_time datetime
)

create table oa_export_file_info(
    id int NOT NULL auto_increment,
    uid varchar(100),
    file_name varchar(100),
    file_path varchar(200),
    status varchar(10),
    create_by varchar(100),
    create_time datetime,
	update_by varchar(100),
	last_update_time datetime
)
