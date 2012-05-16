# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table channel (
  id                        integer auto_increment not null,
  name                      varchar(255),
  topic                     varchar(255),
  isread                    tinyint(1) default 0,
  priv                      tinyint(1) default 0,
  constraint pk_channel primary key (id))
;

create table file (
  id                        integer auto_increment not null,
  name                      varchar(255),
  type                      varchar(255),
  size                      double,
  date                      datetime,
  uid_id                    integer,
  constraint pk_file primary key (id))
;

create table groups (
  id                        integer auto_increment not null,
  name                      varchar(255),
  modified                  datetime,
  constraint pk_groups primary key (id))
;

create table message (
  id                        integer auto_increment not null,
  content                   varchar(255),
  type                      varchar(255),
  date                      datetime,
  modified                  datetime,
  user_id                   integer,
  constraint pk_message primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  username                  varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  online                    tinyint(1) default 0,
  admin                     tinyint(1) default 0,
  lastlogin                 datetime,
  constraint pk_user primary key (id))
;


create table channel_user (
  channel_id                     integer not null,
  user_id                        integer not null,
  constraint pk_channel_user primary key (channel_id, user_id))
;

create table channel_groups (
  channel_id                     integer not null,
  groups_id                      integer not null,
  constraint pk_channel_groups primary key (channel_id, groups_id))
;

create table channel_message (
  channel_id                     integer not null,
  message_id                     integer not null,
  constraint pk_channel_message primary key (channel_id, message_id))
;

create table channel_file (
  channel_id                     integer not null,
  file_id                        integer not null,
  constraint pk_channel_file primary key (channel_id, file_id))
;

create table user_groups (
  user_id                        integer not null,
  groups_id                      integer not null,
  constraint pk_user_groups primary key (user_id, groups_id))
;
alter table file add constraint fk_file_uid_1 foreign key (uid_id) references user (id) on delete restrict on update restrict;
create index ix_file_uid_1 on file (uid_id);
alter table message add constraint fk_message_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_message_user_2 on message (user_id);



alter table channel_user add constraint fk_channel_user_channel_01 foreign key (channel_id) references channel (id) on delete restrict on update restrict;

alter table channel_user add constraint fk_channel_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table channel_groups add constraint fk_channel_groups_channel_01 foreign key (channel_id) references channel (id) on delete restrict on update restrict;

alter table channel_groups add constraint fk_channel_groups_groups_02 foreign key (groups_id) references groups (id) on delete restrict on update restrict;

alter table channel_message add constraint fk_channel_message_channel_01 foreign key (channel_id) references channel (id) on delete restrict on update restrict;

alter table channel_message add constraint fk_channel_message_message_02 foreign key (message_id) references message (id) on delete restrict on update restrict;

alter table channel_file add constraint fk_channel_file_channel_01 foreign key (channel_id) references channel (id) on delete restrict on update restrict;

alter table channel_file add constraint fk_channel_file_file_02 foreign key (file_id) references file (id) on delete restrict on update restrict;

alter table user_groups add constraint fk_user_groups_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_groups add constraint fk_user_groups_groups_02 foreign key (groups_id) references groups (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table channel;

drop table channel_user;

drop table channel_groups;

drop table channel_message;

drop table channel_file;

drop table file;

drop table groups;

drop table user_groups;

drop table message;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

