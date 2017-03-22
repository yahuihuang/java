一、restfulTest Project<br/>
  1.create table and stored procedure in mysql database<br/>
    1) DB/1.cr_table.sql<br/>
    2) DB/2.cr_SP.sql<br/>
  2.maven command for pom.xml<br/>
  3.change config<br/>
    1) src/sysConfig.xml : modify pwd.deskey value , the value you can edit for your prefer<br/>
    2) WebContent/WEB-INF/applicationContext_WA.xml<br/>
    a.change mysql db connection - jdbcUrl<br/>
    b.change mysql db user and password,<br/>
      the password should be encrypt, you can edit WebContent/DesTest.jsp sIn variable, <br/>
      and get output string , then put in password property.<br/>
  4.insert some data in mysql table : WABlog<br/>
  4.test<br/>
  1) http://127.0.0.1:8080/restfulTest/services/blogservice/getpost/1<br/>
     http://127.0.0.1:8080/restfulTest/services/blogservice/getpost/1.xml<br/>
  Request: None<br/>
  Content-Type: application/x-www-form-urlencoded<br/>
  accept: application/json<br/>
  
  2) http://127.0.0.1:8080/restfulTest/services/blogservice/getallpost<br/>
     http://127.0.0.1:8080/restfulTest/services/blogservice/getallpost.xml<br/>
  Request: None<br/>
  Content-Type: application/x-www-form-urlencoded<br/>
  accept: application/json<br/>
