# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table channel (
  id                        integer not null,
  name                      varchar(255),
  topic                     varchar(255),
  isread                    boolean,
  constraint pk_channel primary key (id))
;

create table file (
  id                        integer not null,
  type                      varchar(255),
  size                      double,
  owner_id                  integer,
  timestamp                 timestamp,
  constraint pk_file primary key (id))
;

create table groups (
  id                        integer not null,
  name                      varchar(255),
  constraint pk_groups primary key (id))
;

create table user (
  id                        integer not null,
  username                  varchar(255),
  password                  varchar(255),
  online                    boolean,
  prename                   varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
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

create table channel_file (
  channel_id                     integer not null,
  file_id                        integer not null,
  constraint pk_channel_file primary key (channel_id, file_id))
;
create sequence channel_seq;

create sequence file_seq;

create sequence groups_seq;

create sequence user_seq;




alter table channel_user add constraint fk_channel_user_channel_01 foreign key (channel_id) references channel (id) on delete restrict on update restrict;

alter table channel_user add constraint fk_channel_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table channel_groups add constraint fk_channel_groups_channel_01 foreign key (channel_id) references channel (id) on delete restrict on update restrict;

alter table channel_groups add constraint fk_channel_groups_groups_02 foreign key (groups_id) references groups (id) on delete restrict on update restrict;

alter table channel_file add constraint fk_channel_file_channel_01 foreign key (channel_id) references channel (id) on delete restrict on update restrict;

alter table channel_file add constraint fk_channel_file_file_02 foreign key (file_id) references file (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists channel;

drop table if exists channel_user;

drop table if exists channel_groups;

drop table if exists channel_file;

drop table if exists file;

drop table if exists groups;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists channel_seq;

drop sequence if exists file_seq;

drop sequence if exists groups_seq;

drop sequence if exists user_seq;

