create table if not exists items (
   id serial primary key,
   name text,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);