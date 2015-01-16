create keyspace meetup
with replication={
    'class':'NetworkTopologyStrategy',
    'LYON':2,'PARIS':2};

create table metric (
    host varchar,
    name varchar,
    date timestamp,
    value bigint,
    primary key ((host,name),date)
);
describe keyspaces;
describe keyspace meetup;
use meetup;
describe tables;
describe table metric;
select * from metric;
insert into metric(host,name,date,value)
   values ('localhost','cpu',dateOf(now()), 12);
