alter table demo add constraint fk_demo_on_users foreign key (user_id) references users(id);
alter table users add constraint fk_users_on_kycs foreign key (kyc_id) references kycs(id);