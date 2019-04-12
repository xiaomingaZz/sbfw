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
	buffer.append("<bsrq>"+UtilComm.encode("2001-12-17")+"</bsrq>");
	buffer.append("<gyid>"+UtilComm.encode("4096")+"</gyid>");
	buffer.append("<datas>");
	buffer.append("<data>");
	buffer.append("<id>"+UtilComm.encode("4096")+"</id>");
	buffer.append("<ajlist>");
	buffer.append("<aj>");
	buffer.append("<wjmc>"+UtilComm.encode("31_325400000029261.xml")+"</wjmc>");
	buffer.append("<bssj>"+UtilComm.encode("2001-12-17T09:30:47.0Z")+"</bssj>");
	buffer.append("</aj>");
	buffer.append("<aj>");
	buffer.append("<wjmc>"+UtilComm.encode("51_405000000057277.xml")+"</wjmc>");
	buffer.append("<bssj>"+UtilComm.encode("2001-12-17T09:30:47.0Z")+"</bssj>");
	buffer.append("</aj>");
	buffer.append("</ajlist>");
	buffer.append("<jglist>");
	buffer.append("<jg>");
	buffer.append("<wjmc>"+UtilComm.encode("JG_4096.xml")+"</wjmc>");
	buffer.append("<bssj>"+UtilComm.encode("2001-12-17T09:30:47.0Z")+"</bssj>");
	buffer.append("</jg>");
	buffer.append("</jglist>");
	buffer.append("<dellist>");
	buffer.append("<del>");
	buffer.append("<wjmc>"+UtilComm.encode("DEL_4096.xml")+"</wjmc>");
	buffer.append("<bssj>"+UtilComm.encode("2001-12-17T09:30:47.0Z")+"</bssj>");
	buffer.append("</del>");
	buffer.append("</dellist>");
	buffer.append("</data>");
	buffer.append("</datas>");
	buffer.append("</root>");
	DataSbService client = (DataSbService)CenterUtils.getBean("sjsbClient");
	String result = client.importWjList(UtilComm.encode(buffer.toString()));
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