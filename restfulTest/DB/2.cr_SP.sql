-- ###### 1.SP.PROCEDURE WAAddBlog ######
DROP PROCEDURE IF EXISTS WAAddBlog;

CREATE PROCEDURE WAAddBlog(
  IN Title nvarchar(1024),
  IN Slogan nvarchar(1024),
  IN Description nvarchar(2048),
  IN Content text,
  IN ImgKind int,
  IN ImgUrl varchar(1024),
  IN ModifyEmp varchar(8) )
BEGIN
	declare new_identity INT;
	set new_identity = 0;

    INSERT WABlog(Title,Slogan,Description,ModifyTime,ModifyEmp) VALUES(Title, Slogan, Description, NOW(), ModifyEmp);
    SET new_identity = LAST_INSERT_ID();
	
	INSERT WABlogContent(BlogId,Content,ModifyTime,ModifyEmp) VALUES(new_identity, Content, NOW(), ModifyEmp);
	INSERT WABlogImage(BlogId,Seq,Kind,Image,ModifyTime,ModifyEmp) VALUES(new_identity, 1, ImgKind, ImgUrl, NOW(), ModifyEmp);
END;

-- ###### 2.SP.PROCEDURE WAModifyBlog ######
DROP PROCEDURE IF EXISTS WAModifyBlog;

CREATE PROCEDURE WAModifyBlog(
  IN blogId_in int,
  IN Title_in nvarchar(1024),
  IN Slogan_in nvarchar(1024),
  IN Description_in nvarchar(2048),
  IN Content_in text,
  IN ImgKind_in int,
  IN ImgUrl_in varchar(1024),
  IN ModifyEmp_in varchar(8) )
BEGIN
	
	declare new_identity INT;
	set new_identity = 0;
	if (blogId_in <= 0)
	then
		INSERT WABlog(Title,Slogan,Description,Status,ModifyTime,ModifyEmp) VALUES(Title_in, Slogan_in, Description_in, 1,NOW(), ModifyEmp_in);
		SET new_identity = LAST_INSERT_ID();
		
		INSERT WABlogContent(BlogId,Content,ModifyTime,ModifyEmp) VALUES(new_identity, Content_in, NOW(), ModifyEmp_in);
		INSERT WABlogImage(BlogId,Seq,Kind,Image,ModifyTime,ModifyEmp) VALUES(new_identity, 1, ImgKind_in, ImgUrl_in, NOW(), ModifyEmp_in);
		
		select t1.* from WABlog t1 WHERE t1.blogId=new_identity;
	end if;
	
	if (blogId_in > 0)
	then	
			UPDATE WABlog
			   SET Title       = Title_in,
				   Slogan      = Slogan_in,
				   Description = Description_in,
				   ModifyTime  = NOW(),
				   ModifyEmp   = ModifyEmp_in
			 WHERE blogId      = blogId_in
			   AND (Title <> Title_in OR Slogan <> Slogan_in OR Description <> Description_in);
		
			UPDATE WABlogContent
			   SET Content     = Content_in,
				   ModifyTime  = NOW(),
				   ModifyEmp   = ModifyEmp_in
			 WHERE blogId      = blogId_in
			   AND Content <> Content_in;
	    
			UPDATE WABlogImage
			   SET Kind        = ImgKind_in,
				   Image       = ImgUrl_in,
				   ModifyTime  = NOW(),
				   ModifyEmp   = ModifyEmp_in 
			 WHERE blogId      = blogId_in
			   AND Seq         = 1
			   AND (Kind <> ImgKind_in OR Image <> ImgUrl_in);
		
		select t1.* from WABlog t1 WHERE t1.blogId=blogId_in; 
    end if;
END;