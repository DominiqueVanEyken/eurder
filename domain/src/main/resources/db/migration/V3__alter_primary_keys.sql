-- CHANGE PRIMARY KEY FROM COMPOSITE KEY TO ITEM_GROUP_ID
create sequence item_group_seq start with 1 increment by 1;

alter table item_group
drop constraint item_group_pkey;

alter table item_group
add column item_group_id integer;

alter table item_group
add constraint item_group_pkey primary key (item_group_id);
