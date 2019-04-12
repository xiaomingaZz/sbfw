<%@page import="tdh.util.UtilComm"%>
<%@page import="tdh.util.CenterUtils"%>
<%@page import="org.apache.cxf.endpoint.Client"%>
<%@page import="tdh.sjsb.zhiliangycService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	StringBuilder buffer = new StringBuilder();
	buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	buffer.append("<root><fydm>4096</fydm><lx>T</lx></root>");	
	zhiliangycService client = (zhiliangycService)CenterUtils.getBean("zhiliangycClient");
	System.out.println(buffer.toString());
	String result = client.zlgzYcList(buffer.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%= result%>
</body>
</html>