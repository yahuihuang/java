USE `warrantweb`

-- ###### 1.WABlog ������峹�� ######
DROP TABLE IF EXISTS WABlog;
CREATE TABLE WABlog(
	BlogId int AUTO_INCREMENT NOT NULL COMMENT '',
	Title nvarchar(1024) NOT NULL COMMENT '���D',
	Slogan nvarchar(1024) NOT NULL COMMENT '���z',
	Description nvarchar(2048) NOT NULL COMMENT 'Slogan',
	Status int NOT NULL COMMENT '�w�]:1�s�褤,1:�s�褤,2:�f�֤�,5:�w�W�[,6:����,9:�w�U�[',
	ModifyTime datetime NOT NULL COMMENT '�ק�ɶ�:�ɶ��榡YYYY-MM-DD HH:MM:SS.SSS',
	ModifyEmp varchar(8) NOT NULL COMMENT '�ק�H��',
 PRIMARY KEY (BlogId ASC)
) COMMENT='������峹��';

SELECT * FROM WABlog;

-- ###### 2.WAIPDeny IP���~�� ######
DROP TABLE IF EXISTS WAIPDeny;
CREATE TABLE WAIPDeny(
	NetworkIp char(16) NOT NULL COMMENT '���qIP',
	SubnetMask char(16) NOT NULL COMMENT 'SUBNET_MASK',
	ModifyTime datetime COMMENT '�ק�ɶ�:�ɶ��榡YYYY-MM-DD HH:MM:SS.SSS',
	ModifyEmp varchar(8) NOT NULL COMMENT '�ק�H��',
 PRIMARY KEY (NetworkIp ASC)
) COMMENT='IP���~��';

SELECT * FROM WAIPDeny;