# --- !Ups
create table "films" (
  "id" bigint generated by default as identity(start with 1) not null primary key,
  "title" varchar not null,
  "length" int not null
);

# --- !Downs
drop table "films" if exists;
