create table track (
  id serial primary key,
  title varchar(200) not null,
  album_title varchar(200),
  artist varchar(200) not null,
  year integer,
  genre varchar(200),
  unique (title, artist)
);


create table track_fingerprint (
  track_id integer references track(id),
  fingerprint integer,
  unique (track_id, fingerprint)
);


create index track_id_index on track(id);
create index fingerprint_hash_index on track_fingerprint(fingerprint);