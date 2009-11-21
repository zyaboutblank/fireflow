drop table T_Biz_EmailMock if exists;
drop table T_Biz_LeaveApplicationInfo if exists;
drop table T_Biz_LeaveApprovalInfo if exists;
drop table T_FF_RT_TASKINSTANCE if exists;
create table T_Biz_EmailMock (ID varchar(50) not null, user_Id varchar(50) not null, content varchar(512) not null, create_Time timestamp not null, primary key (ID));
create table T_Biz_LeaveApplicationInfo (ID varchar(50) not null, sn varchar(50) not null, leaveReason varchar(50), fromDate varchar(50), toDate varchar(50), leaveDays integer, applicant_Id varchar(50), applicant_Name varchar(50), submitTime varchar(50), approval_Flag bit, approval_Detail varchar(50), approver varchar(50), approval_Time timestamp, primary key (ID), unique (sn));
create table T_Biz_LeaveApprovalInfo (ID varchar(50) not null, sn varchar(50) not null, approver varchar(50), approval_Flag bit, detail varchar(100), approval_Time timestamp, primary key (ID));
create table T_FF_RT_TASKINSTANCE (ID varchar(50) not null, BIZ_TYPE varchar(250) not null, TASK_ID varchar(300) not null, ACTIVITY_ID varchar(200) not null, NAME varchar(100) not null, DISPLAY_NAME varchar(128), STATE integer not null, SUSPENDED bit not null, TASK_TYPE varchar(10), CREATED_TIME timestamp not null, STARTED_TIME timestamp, EXPIRED_TIME timestamp, END_TIME timestamp, ASSIGNMENT_STRATEGY varchar(10), PROCESSINSTANCE_ID varchar(50) not null, PROCESS_ID varchar(100) not null, VERSION integer not null, TARGET_ACTIVITY_ID varchar(100), FROM_ACTIVITY_ID varchar(600), STEP_NUMBER integer not null, CAN_BE_WITHDRAWN bit not null, primary key (ID));
create index idx_taskInst_stepNb on T_FF_RT_TASKINSTANCE (STEP_NUMBER);
