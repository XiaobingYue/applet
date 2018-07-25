-- Add/modify columns
alter table T_THIRD_PERSON add TF_MOBILE varchar2(15);

alter table T_THIRD_PERSON add TF_COMM_TYPE varchar2(32);

alter table T_THIRD_PERSON add POS_TF_MOBILE varchar2(15);

alter table T_THIRD_PERSON add POS_TF_COMM_TYPE varchar2(32);

alter table T_THIRD_PERSON add USB_KEY_MOBILE varchar2(15);

alter table T_THIRD_PERSON add USB_KEY_COMM_TYPE varchar2(32);

-- Add comments to the columns
comment on column T_THIRD_PERSON.TF_MOBILE
is 'TF卡手机号';
comment on column T_THIRD_PERSON.TF_COMM_TYPE
is 'TF卡运营商';
comment on column T_THIRD_PERSON.POS_TF_MOBILE
is 'POS机TF卡手机号';
comment on column T_THIRD_PERSON.POS_TF_COMM_TYPE
is 'POS机TF卡运营商';
comment on column T_THIRD_PERSON.USB_KEY_MOBILE
is 'USBKEY手机号';
comment on column T_THIRD_PERSON.USB_KEY_COMM_TYPE
is 'USBKEY运营商';
-- 修改中间表中card_type字段长度
alter table T_THIRD_DEVICE modify CARD_TYPE VARCHAR2(32);