create table if not exists private.revinfo(
	rev integer generated by default as identity,
	revtstmp bigint,
	primary key (rev)
);


create table if not exists private.account(
	id varchar(255) not null,
	username varchar(255) not null,
	password varchar(255) not null,
	full_name varchar(255) not null,
	birthday date null,
	gender varchar(255),
	phone varchar(255),
	email varchar(255) not null,
	address varchar(255),
	avatar_file_id varchar(255),
	role_code varchar(255) not null,
	status varchar(255) not null,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_account primary key (id),
	constraint account_uk1 unique (username),
	constraint account_uk2 unique (email),
	constraint account_uk3 unique (phone)
);

create table if not exists private.account_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	username varchar(255),
	password varchar(255),
	full_name varchar(255),
	birthday date,
	gender varchar(10),
	phone varchar(11),
	email varchar(255),
	address varchar(255),
	avatar_file_id varchar(255),
	role_code varchar(255),
	status int,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_account_aud primary key (rev, id),
	constraint FK_account_aud foreign key (rev) references private.revinfo
);

create table if not exists private.title(
    id varchar(255) not null,
    title_code varchar(255) not null,
    title_name varchar(255) not null,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_title primary key (id),
    constraint UK1_title unique (title_code),
    constraint UK2_title unique (title_name)
);

create table if not exists private.title_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    title_code varchar(255),
    title_name varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_title_aud primary key (rev, id),
    constraint FK_title_aud foreign key (rev) references private.revinfo
);

create table if not exists private.user_activity(
    id varchar(255) not null,
    username varchar(255) not null,
    activity_type varchar(255) not null,
    description varchar(255),
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_activity primary key (id)
);

create table if not exists private.user_activity_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    activity_type varchar(255),
    description varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_activity_aud primary key (rev, id),
    constraint FK_user_activity_aud foreign key (rev) references private.revinfo
);

create table if not exists private.user_favourite(
    id varchar(255) not null,
    username varchar(255) not null,
    story_id varchar(255) not null,
    favourite_status boolean,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_favourite primary key (id),
    constraint UK1_user_favourite unique (username, story_id)
);

create table if not exists private.user_favourite_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    story_id varchar(255),
    favourite_status boolean,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_favourite_aud primary key (rev, id),
    constraint FK_user_favourite_aud foreign key (rev) references private.revinfo
);

create table if not exists private.user_permission(
    id varchar(255) not null,
    username varchar(255) not null,
    permission_code varchar(255) not null,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_permission primary key (id),
    constraint UK1_user_permission unique (username, permission_code)
);

create table if not exists private.user_permission_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    permission_code varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_permission_aud primary key (rev, id),
    constraint FK_user_permission_aud foreign key (rev) references private.revinfo
);
create table if not exists private.user_permission(
    id varchar(255) not null,
    username varchar(255) not null,
    permission_code varchar(255) not null,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_permission primary key (id),
    constraint UK1_user_permission unique (username, permission_code)
);

create table if not exists private.user_permission_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    permission_code varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_permission_aud primary key (rev, id),
    constraint FK_user_permission_aud foreign key (rev) references private.revinfo
);

create table if not exists private.user_title(
    id varchar(255) not null,
    username varchar(255) not null,
    title_code varchar(255) not null,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_title primary key (id),
    constraint UK1_user_title unique (username, title_code)
);

create table if not exists private.user_title_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    title_code varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_title_aud primary key (rev, id),
    constraint FK_user_title_aud foreign key (rev) references private.revinfo
);

create table if not exists private.user_setting(
    id varchar(255) not null,
    username varchar(255) not null,
    theme_code varchar(255) not null,
    font varchar(255),
    font_size int,
    scroll_speed float,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_setting primary key (id)
);

create table if not exists private.user_setting_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    theme_code varchar(255),
    font varchar(255),
    font_size int,
    scroll_speed float,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_setting_aud primary key (rev, id),
    constraint FK_user_setting_aud foreign key (rev) references private.revinfo
);

create table if not exists private.user_notification(
    id varchar(255) not null,
    username varchar(255) not null,
    content varchar(255),
    checking_status boolean,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_notification primary key (id)
);

create table if not exists private.user_notification_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    content varchar(255),
    checking_status boolean,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_notification_aud primary key (rev, id),
    constraint FK_user_notification_aud foreign key (rev) references private.revinfo
);

create table if not exists private.author_subscription(
    id varchar(255) not null,
    username varchar(255) not null,
    author_id varchar(255) not null,
    send_to_mail boolean,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_author_subscription primary key (id),
    constraint UK1_author_subscription unique (username, author_id)
);

create table if not exists private.author_subscription_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    author_id varchar(255),
    send_to_mail boolean,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_author_subscription_aud primary key (rev, id),
    constraint FK_author_subscription_aud foreign key (rev) references private.revinfo
);

create table if not exists private.user_wallet(
    id varchar(255) not null,
    username varchar(255) not null,
    money decimal,
    point integer,
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_wallet primary key (id)
);

create table if not exists private.user_wallet_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    money decimal,
    point integer,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_wallet_aud primary key (rev, id),
    constraint FK_user_wallet_aud foreign key (rev) references private.revinfo
);

create table if not exists private.user_badge(
    id varchar(255) not null,
    username varchar(255) not null,
    archieve_date timestamp,
    archievement varchar(255),
    version int not null,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_badge primary key (id)
);

create table if not exists private.user_badge_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    archieve_date timestamp,
    archievement varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_user_badge_aud primary key (rev, id),
    constraint FK_user_badge_aud foreign key (rev) references private.revinfo
);

create table if not exists private.comment(
	id varchar(255) not null,
	content text null,
	parents_comment_id varchar(255) null,
	story_id varchar(255) not null,
	username varchar(255) not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_comment primary key (id)
);

create table if not exists private.comment_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	content text,
	parents_comment_id varchar(255),
	story_id varchar(255),
	username varchar(255),
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_comment_aud primary key (rev, id),
	constraint FK_comment_aud foreign key (rev) references private.revinfo
);

create table if not exists private.review(
	id varchar(255) not null,
	title varchar(255) not null,
	content text not null,
	point float not null,
	story_id varchar(255) not null,
	username varchar(255) not null,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	last_updated_date timestamp,
	last_updated_by varchar(255),
	constraint PK_review primary key (id),
	constraint review_uk1 unique (story_id, username)
);

create table if not exists private.review_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	title varchar(255),
	content text,
	point float,
	story_id varchar(255),
	username varchar(255),
	created_at timestamp,
	created_by varchar(255),
	last_updated_date timestamp,
	last_updated_by varchar(255),
	constraint PK_review_aud primary key (rev, id),
	constraint FK_review_aud foreign key (rev) references private.revinfo
);

create table if not exists private.role(
	role_code varchar(255) not null,
	role_name varchar(255) not null,
	description varchar(255),
	constraint PK_role primary key (role_code),
	constraint role_uk1 unique (role_name)
);

create table if not exists private.status(
	status_code varchar(255) not null,
	status_name varchar(255) not null,
	description varchar(255),
	constraint PK_status primary key (status_code),
	constraint status_uk1 unique (status_name)
);

create table if not exists private.story_statistic(
	id varchar(255) not null,
	story_id varchar(255) not null,
	average_point float not null,
	number_of_reviews bigint not null,
	number_of_views bigint not null,
	number_of_follows bigint not null,
	number_of_favourites bigint not null,
	constraint PK_story_statistic primary key (id),
	constraint story_statistic_uk1 unique (story_id)
);

create table if not exists private.story_statistic_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	story_id varchar(255),
	average_point float,
	number_of_reviews bigint,
	number_of_views bigint,
	number_of_follows bigint,
	number_of_favourites bigint,
	constraint PK_story_statistic_aud primary key (rev, id),
	constraint FK_story_statistic_aud foreign key (rev) references private.revinfo
);

create table if not exists private.story_tag(
	id varchar(255) not null,
	story_id varchar(255) not null,
	tag varchar(255) not null,
	constraint PK_story_tag primary key (id),
	constraint story_tag_uk1 unique (story_id, tag)
);	

create table if not exists private.story_tag_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	story_id varchar(255),
	tag varchar(255),
	constraint PK_story_tag_aud primary key (rev, id),
	constraint FK_story_tag_aud foreign key (rev) references private.revinfo
);	

create table if not exists private.reading_progress(
	id varchar(255) not null,
	user_id varchar(255) not null,
	story_id varchar(255) not null,
	chapter_id varchar(255) not null,
	last_view_at timestamp not null,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_user_reading_progress primary key (id)
);

create table if not exists private.reading_progress_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	user_id varchar(255),
	story_id varchar(255),
	chapter_id varchar(255),
	last_view_at timestamp,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_reading_progress_aud primary key (rev, id),
	constraint FK_reading_progress_aud foreign key (rev) references private.revinfo
);

create table if not exists private.activity_type(
	id varchar(255) not null,
	activity_type varchar(255) not null,
	activity_name varchar(255) not null,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_activity_type primary key (id),
	constraint uk1_activity_type unique (activity_type),
	constraint uk2_activity_type unique (activity_name)
);

create table if not exists private.activity_type_aud (
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	activity_type varchar(255),
	activity_name varchar(255),
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_activity_type_aud primary key (rev, id),
	constraint FK_activity_type_aud foreign key (rev) references private.revinfo
);

create table if not exists private.content_type(
	id varchar(255) not null,
	content_type varchar(255) not null,
	content_name varchar(255) not null,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_content_type primary key (id),
	constraint uk1_content_type unique (content_type),
	constraint uk2_content_type unique (content_name)
);

create table if not exists private.content_type_aud (
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	content_type varchar(255),
	content_name varchar(255),
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_content_type_aud primary key (rev, id),
	constraint FK_content_type_aud foreign key (rev) references private.revinfo
);

create table if not exists private.admin_activity (
	id varchar(255) not null,
	username varchar(255) not null,
	activity_type varchar(255) not null,
	content_type varchar(255),
	content varchar(255),
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_admin_activity primary key (id)
);

create table if not exists private.admin_activity_aud (
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	username varchar(255),
	activity_type varchar(255),
	content_type varchar(255),
	content varchar(255),
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_admin_activity_aud primary key (rev, id),
	constraint FK_admin_activity_aud foreign key (rev) references private.revinfo
);

create table if not exists private.report_type (
	id varchar(255) not null,
	report_type varchar(255) not null,
	report_name varchar(255) not null,
	version integer not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_report_type primary key (id),
	constraint UK1_report_type unique (report_type),
	constraint UK2_report_type unique (report_name)
);

create table if not exists private.report_type_aud (
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	report_type varchar(255),
	report_name varchar(255),
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_report_type_aud primary key (rev, id),
	constraint FK_report_type_aud foreign key (rev) references private.revinfo
);

create table if not exists private.chapter_report (
	id varchar(255) not null,
	report_type varchar(255) not null,
	username varchar(255) not null,
	title varchar(255) not null,
	content text,
	version integer not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_chapter_report primary key (id)
);

create table if not exists private.chapter_report_aud (
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	report_type varchar(255),
	username varchar(255),
	title varchar(255),
	content text,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_chapter_report_aud primary key (rev, id),
	constraint FK_chapter_report_aud foreign key (rev) references private.revinfo
);

create table if not exists private.event (
	id varchar(255) not null,
	title varchar(255) not null,
	description varchar(255) not null,
	avatar_file_id varchar(255) not null,
	content text not null,
	start_date timestamp,
	end_date timestamp,
	version integer not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_event primary key (id)
);

create table if not exists private.event_aud (
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	title varchar(255),
	description varchar(255),
	avatar_file_id varchar(255),
	content text,
	start_date timestamp,
	end_date timestamp,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_event_aud primary key (rev, id),
	constraint FK_event_aud foreign key (rev) references private.revinfo
);

	
create table if not exists private.file_upload(
	id varchar(255) not null,
	file_name varchar(255) not null,
	file_url varchar(255) not null,
	file_type varchar(255) not null,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_file_upload primary key (id)
);
	
create table if not exists private.file_upload_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	file_name varchar(255),
	file_url varchar(255),
	file_type varchar(255),
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_file_upload_aud primary key (rev, id),
	constraint FK_file_upload_aud foreign key (rev) references private.revinfo
);

create table if not exists private.group(
	id varchar(255) not null,
	group_owner varchar(255) not null,
	group_vice_owner varchar(255),
	group_name varchar(255) not null,
	description varchar(255),
	avatar_id varchar(255),
	access_type varchar(255) not null,
	version integer not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_group primary key (id)
);

create table if not exists private.group_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    group_owner varchar(255),
    group_vice_owner varchar(255),
    group_name varchar(255),
    description varchar(255),
    avatar_id varchar(255),
    access_type varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_group_aud primary key (rev, id),
    constraint FK_group_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.group_member (
    id VARCHAR(255) NOT NULL,
    group_id VARCHAR(255),
    username VARCHAR(255),
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_group_member PRIMARY KEY (id)
);

create table if not exists private.group_member_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    group_id varchar(255),
    username varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_group_member_aud primary key (rev, id),
    constraint FK_group_member_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.guideline (
    id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_guideline PRIMARY KEY (id)
);

create table if not exists private.guideline_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    title varchar(255),
    description varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_guideline_aud primary key (rev, id),
    constraint FK_guideline_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.level (
    id VARCHAR(255) NOT NULL,
    level_id VARCHAR(255) NOT NULL,
    level_name VARCHAR(255) NOT NULL,
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_level PRIMARY KEY (id),
    CONSTRAINT UK1_level UNIQUE (level_id),
    CONSTRAINT UK2_level UNIQUE (level_name)
);

create table if not exists private.level_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    level_id varchar(255),
    level_name varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_level_aud primary key (rev, id),
    constraint FK_level_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.payment_history (
    id VARCHAR(255) NOT NULL,
    username VARCHAR(255),
    payment_method VARCHAR(255),
    discount_code VARCHAR(255),
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_payment_history PRIMARY KEY (id)
);

create table if not exists private.payment_history_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    username varchar(255),
    payment_method varchar(255),
    discount_code varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_payment_history_aud primary key (rev, id),
    constraint FK_payment_history_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.payment_history_detail (
    id VARCHAR(255) NOT NULL,
    history_id VARCHAR(255),
    story_id VARCHAR(255),
    current_price DECIMAL(19,4),
    cost DECIMAL(19,4),
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_payment_history_detail PRIMARY KEY (id),
    CONSTRAINT UK1_payment_history_detail UNIQUE (history_id, story_id)
);

create table if not exists private.payment_history_detail_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    history_id varchar(255),
    story_id varchar(255),
    current_price decimal(19,4),
    cost decimal(19,4),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_payment_history_detail_aud primary key (rev, id),
    constraint FK_payment_history_detail_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.payment_method (
    id VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_payment_method PRIMARY KEY (id),
    CONSTRAINT UK1_payment_method UNIQUE (payment_method),
    CONSTRAINT UK2_payment_method UNIQUE (name)
);

create table if not exists private.payment_method_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    payment_method varchar(255),
    name varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_payment_method_aud primary key (rev, id),
    constraint FK_payment_method_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.discount_code (
    id VARCHAR(255) NOT NULL,
    discount_code VARCHAR(255) NOT NULL,
    discount_name VARCHAR(255),
    avatar_file_id VARCHAR(255),
    description VARCHAR(255),
    specified_payment_method VARCHAR(255),
    specified_story_id VARCHAR(255),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_discount_code PRIMARY KEY (id),
    CONSTRAINT UK1_discount_code UNIQUE (discount_code)
);

create table if not exists private.discount_code_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    discount_code varchar(255),
    discount_name varchar(255),
    avatar_file_id varchar(255),
    description varchar(255),
    specified_payment_method varchar(255),
    specified_story_id varchar(255),
    start_date timestamp,
    end_date timestamp,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_discount_code_aud primary key (rev, id),
    constraint FK_discount_code_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.permission (
    id VARCHAR(255) NOT NULL,
    permission_code VARCHAR(255) NOT NULL,
    permission_name VARCHAR(255) NOT NULL,
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_permission PRIMARY KEY (id),
    CONSTRAINT UK1_permission UNIQUE (permission_code),
    CONSTRAINT UK2_permission UNIQUE (permission_name)
);

create table if not exists private.permission_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    permission_code varchar(255),
    permission_name varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_permission_aud primary key (rev, id),
    constraint FK_permission_aud foreign key (rev) references private.revinfo
);

create table if not exists private.story(
	id varchar(255) not null,
	story_id varchar(255) not null,
	title varchar(255) not null,
	author_id varchar(255) not null,
	avatar_file_id varchar(255) not null,
	description text not null,
	chapter_range varchar(255) not null,
	total_chapters varchar(255) not null,
	status_code varchar(255) not null,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_story primary key (id),
	constraint story_uk1 unique (story_id)
);

create table if not exists private.story_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	story_id varchar(255),
	title varchar(255),
	author_id varchar(255),
	avatar_file_id varchar(255),
	description text,
	chapter_range varchar(255),
	total_chapters varchar(255),
	status_code varchar(255),
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_story_aud primary key (rev, id),
	constraint FK_story_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.story_donate (
    id VARCHAR(255) NOT NULL,
    story_id VARCHAR(255),
    username VARCHAR(255),
    donate_point VARCHAR(255),
    content TEXT,
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_story_donate PRIMARY KEY (id)
);

create table if not exists private.story_donate_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    story_id varchar(255),
    username varchar(255),
    donate_point varchar(255),
    content text,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_story_donate_aud primary key (rev, id),
    constraint FK_story_donate_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.story_subscription (
    id VARCHAR(255) NOT NULL,
    story_id VARCHAR(255),
    username VARCHAR(255),
    send_to_mail BOOLEAN,
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_story_subscription PRIMARY KEY (id),
    CONSTRAINT UK1_story_subscription UNIQUE (story_id, username)
);

create table if not exists private.story_subscription_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    story_id varchar(255),
    username varchar(255),
    send_to_mail boolean,
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_story_subscription_aud primary key (rev, id),
    constraint FK_story_subscription_aud foreign key (rev) references private.revinfo
);

CREATE TABLE IF NOT EXISTS private.story_report (
    id VARCHAR(255) NOT NULL,
    story_id VARCHAR(255),
    report_type VARCHAR(255),
    username VARCHAR(255),
    title VARCHAR,
    content VARCHAR,
    version INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    CONSTRAINT PK_story_report PRIMARY KEY (id)
);

create table if not exists private.story_report_aud(
    rev integer not null,
    revtype integer not null,
    id varchar(255) not null,
    story_id varchar(255),
    report_type varchar(255),
    username varchar(255),
    title varchar(255),
    content varchar(255),
    created_at timestamp,
    created_by varchar(255),
    updated_at timestamp,
    updated_by varchar(255),
    constraint PK_story_report_aud primary key (rev, id),
    constraint FK_story_report_aud foreign key (rev) references private.revinfo
);


create table if not exists private.author(
	id varchar(255) not null,
	author_id varchar(255) not null,
	author_name varchar(255) not null,
	avatar_file_id varchar(255),
	description text,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_author primary key (id),
	constraint author_uk1 unique (author_id)
);

create table if not exists private.author_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	author_id varchar(255),
	author_name varchar(255),
	avatar_file_id varchar(255),
	description text,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_author_aud primary key (rev, id),
	constraint FK_author_aud foreign key (rev) references private.revinfo
);


create table if not exists private.category(
	id varchar(255) not null,
	category_code varchar(255) not null,
	category_name varchar(255) not null,
	description text not null,
	created_date TIMESTAMP,
    created_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    version INT NOT NULL,
	constraint PK_category primary key (id),
	constraint category_uk1 unique (category_code),
	constraint category_uk2 unique (category_name)
);

create table if not exists private.category_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	category_code varchar(255),
	category_name varchar(255),
	description text,
	created_date TIMESTAMP,
    created_by VARCHAR(255),
    last_modified_date TIMESTAMP,
    last_modified_by VARCHAR(255),
    version INT NOT NULL,
	constraint PK_category_aud primary key (rev, id),
	constraint FK_category_aud foreign key (rev) references private.revinfo
);

create table if not exists private.story_category(
	id varchar(255) not null,
	story_id varchar(255) not null,
	category_code varchar(255) not null,
	constraint PK_story_category primary key (id),
	constraint story_category_uk1 unique (story_id, category_code)
);

create table if not exists private.story_category_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	story_id varchar(255),
	category_code varchar(255),
	constraint PK_story_category_aud primary key (rev, id),
	constraint FK_story_category_aud foreign key (rev) references private.revinfo
);

create table if not exists private.chapter(
	id varchar(255) not null,
	chapter_id varchar(255) not null,
	chapter_name varchar(255) not null,
	chapter_content text not null,
	chapter_index int not null,
	status_code varchar(255) not null,
	story_id varchar(255) not null,
	version int not null,
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_chapter primary key (id),
	constraint chapter_uk1 unique (story_id, chapter_index),
	constraint chapter_uk2 unique (story_id, chapter_id)
);

create table if not exists private.chapter_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	chapter_id varchar(255),
	chapter_name varchar(255),
	chapter_content text,
	chapter_index int,
	status_code varchar(255),
	story_id varchar(255),
	created_at timestamp,
	created_by varchar(255),
	updated_at timestamp,
	updated_by varchar(255),
	constraint PK_chapter_aud primary key (rev, id),
	constraint FK_chapter_aud foreign key (rev) references private.revinfo
);


create table if not exists private.api_response_message(
	id varchar(255) not null,
	response_message_id varchar(255) not null,
	message_type varchar(255) not null,
	summary_message_pattern varchar(255) not null,
	detail_message_pattern varchar(255) not null,
	constraint PK_api_response_message primary key (id),
	constraint uk1_api_response_message unique (response_message_id)
);

create table if not exists private.api_response_message_aud(
	rev integer not null,
	revtype integer not null,
	id varchar(255) not null,
	response_message_id varchar(255),
	message_type varchar(255),
	summary_message_pattern varchar(255),
	detail_message_pattern varchar(255),
	constraint PK_api_response_message_aud primary key (rev, id),
	constraint FK_api_response_message_aud foreign key (rev) references private.revinfo
);

