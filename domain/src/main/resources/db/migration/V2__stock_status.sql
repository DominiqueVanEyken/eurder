-- ADD COLUMN STOCK_STATUS TO ITEM
alter table item
add column stock_status varchar(16) not null default 'UNKNOWN';