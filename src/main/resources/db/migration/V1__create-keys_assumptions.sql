create TABLE IF NOT EXISTS keys_assumptions(
  id BIGSERIAL NOT NULL,
  key TEXT NOT NULL,
  value TEXT NOT NULL
);

insert into keys_assumptions (key,value) values('costOfcapital', '0.95');

insert into keys_assumptions (key,value) values('volumeAdjInPercentage', '1.0');

insert into keys_assumptions (key,value) values('sameAmortAmountForAllRef', 'true');