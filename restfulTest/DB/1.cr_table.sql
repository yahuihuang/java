USE `warrantweb`

-- ###### 1.WABlog 部落格文章檔 ######
DROP TABLE IF EXISTS WABlog;
CREATE TABLE WABlog(
	BlogId int AUTO_INCREMENT NOT NULL COMMENT '',
	Title nvarchar(1024) NOT NULL COMMENT '標題',
	Slogan nvarchar(1024) NOT NULL COMMENT '概述',
	Description nvarchar(2048) NOT NULL COMMENT 'Slogan',
	Status int NOT NULL COMMENT '預設:1編輯中,1:編輯中,2:審核中,5:已上架,6:取消,9:已下架',
	ModifyTime datetime NOT NULL COMMENT '修改時間:時間格式YYYY-MM-DD HH:MM:SS.SSS',
	ModifyEmp varchar(8) NOT NULL COMMENT '修改人員',
 PRIMARY KEY (BlogId ASC)
) COMMENT='部落格文章檔';

SELECT * FROM WABlog;

-- ###### 2.WAIPDeny IP除外檔 ######
DROP TABLE IF EXISTS WAIPDeny;
CREATE TABLE WAIPDeny(
	NetworkIp char(16) NOT NULL COMMENT '網段IP',
	SubnetMask char(16) NOT NULL COMMENT 'SUBNET_MASK',
	ModifyTime datetime COMMENT '修改時間:時間格式YYYY-MM-DD HH:MM:SS.SSS',
	ModifyEmp varchar(8) NOT NULL COMMENT '修改人員',
 PRIMARY KEY (NetworkIp ASC)
) COMMENT='IP除外檔';

SELECT * FROM WAIPDeny;