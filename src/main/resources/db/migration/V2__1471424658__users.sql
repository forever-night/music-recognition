create table "user" (
  id serial primary key, 
  username varchar(128),
  password varchar(128),
  role varchar default 'USER',
  enabled boolean not null default true,
  created_at timestamptz
);