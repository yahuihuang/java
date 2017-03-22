<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ page import="com.tssco.util.DesUtil" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
<%
	try {		
		DesUtil des = new DesUtil();
		String sIn = "IDDT";
		String sOut = des.encrypt(sIn);
		out.println(sIn + " => " + sOut);
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
</body>
</html>