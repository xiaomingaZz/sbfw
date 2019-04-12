<%@page import="tdh.util.UtilComm"%>
<%@page import="tdh.util.CenterUtils"%>
<%@page import="org.apache.cxf.endpoint.Client"%>
<%@page import="tdh.sjsb.DataSbService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	StringBuilder buffer = new StringBuilder();
	buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	buffer.append("<root>");
	buffer.append("<tjsj>"+UtilComm.encode("2017-06-08T17:10:00.0Z")+"</tjsj>");
	buffer.append("<gyid>"+UtilComm.encode("2250")+"</gyid>");
	buffer.append("<datas>");
	buffer.append("<data>");
	buffer.append("<id>"+UtilComm.encode("2250")+"</id>");
	buffer.append("<drxs>");
	buffer.append("<zs>"+UtilComm.encode("0")+"</zs>");
	buffer.append("<laxq>");
	buffer.append("<ajlx>"+UtilComm.encode("1")+"</ajlx>");
	buffer.append("<sl>"+UtilComm.encode("0")+"</sl>");
	buffer.append("<lalist>");
	buffer.append("</lalist>");
	buffer.append("</laxq>");
	buffer.append("</drxs>");
	buffer.append("<drja>");
	buffer.append("<zs>"+UtilComm.encode("0")+"</zs>");
	buffer.append("<jaxq>");
	buffer.append("<ajlx>"+UtilComm.encode("1")+"</ajlx>");
	buffer.append("<sl>"+UtilComm.encode("0")+"</sl>");
	buffer.append("<jalist>");
	buffer.append("</jalist>");
	buffer.append("</jaxq>");
	buffer.append("</drja>");
	buffer.append("</data>");
	buffer.append("</datas>");
	buffer.append("</root>");
	
	
	DataSbService client = (DataSbService)CenterUtils.getBean("sjsbClient");
	String result = client.importSpdtAndAjList(UtilComm.encode(buffer.toString()));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html>