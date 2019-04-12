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
	buffer.append("<tjsj>"+UtilComm.encode("2001-12-17T09:30:47.0Z")+"</tjsj>");
	buffer.append("<gyid>"+UtilComm.encode("4096")+"</gyid>");
	buffer.append("<datas>");
	buffer.append("<data>");
	buffer.append("<id>"+UtilComm.encode("4096")+"</id>");
	buffer.append("<drxs>");
	buffer.append("<zs>"+UtilComm.encode("4096")+"</zs>");
	buffer.append("<laxq>");
	buffer.append("<ajlx>"+UtilComm.encode("1")+"</ajlx>");
	buffer.append("<sl>"+UtilComm.encode("4096")+"</sl>");
	buffer.append("<lalist>");
	buffer.append("<laxx>");
	buffer.append("<ajbs>"+UtilComm.encode("1")+"</ajbs>");
	buffer.append("<ah>"+UtilComm.encode("String")+"</ah>");
	buffer.append("<sarq>"+UtilComm.encode("1967-08-13")+"</sarq>");
	buffer.append("<larq>"+UtilComm.encode("1967-08-13")+"</larq>");
	buffer.append("<laay>"+UtilComm.encode("4096")+"</laay>");
	buffer.append("<qsbdje>"+UtilComm.encode("3.1415")+"</qsbdje>");
	buffer.append("<ajzlx>"+UtilComm.encode("1")+"</ajzlx>");
	buffer.append("<tqxzpc>"+UtilComm.encode("2")+"</tqxzpc>");
	buffer.append("</laxx>");
	buffer.append("</lalist>");
	buffer.append("</laxq>");
	buffer.append("<laxq>");
	buffer.append("<ajlx>"+UtilComm.encode("2")+"</ajlx>");
	buffer.append("<sl>"+UtilComm.encode("4096")+"</sl>");
	buffer.append("<lalist>");
	buffer.append("<laxx>");
	buffer.append("<ajbs>"+UtilComm.encode("1")+"</ajbs>");
	buffer.append("<ah>"+UtilComm.encode("String")+"</ah>");
	buffer.append("<sarq>"+UtilComm.encode("1967-08-13")+"</sarq>");
	buffer.append("<larq>"+UtilComm.encode("1967-08-13")+"</larq>");
	buffer.append("<laay>"+UtilComm.encode("4096")+"</laay>");
	buffer.append("<qsbdje>"+UtilComm.encode("3.1415")+"</qsbdje>");
	buffer.append("<ajzlx>"+UtilComm.encode("1")+"</ajzlx>");
	buffer.append("<tqxzpc>"+UtilComm.encode("2")+"</tqxzpc>");
	buffer.append("</laxx>");
	buffer.append("</lalist>");
	buffer.append("</laxq>");
	buffer.append("</drxs>");
	buffer.append("<drja>");
	buffer.append("<zs>"+UtilComm.encode("4096")+"</zs>");
	buffer.append("<jaxq>");
	buffer.append("<ajlx>"+UtilComm.encode("1")+"</ajlx>");
	buffer.append("<sl>"+UtilComm.encode("4096")+"</sl>");
	buffer.append("<jalist>");
	buffer.append("<jaxx>");
	buffer.append("<ajbs>"+UtilComm.encode("1")+"</ajbs>");
	buffer.append("<ah>"+UtilComm.encode("String")+"</ah>");
	buffer.append("<jarq>"+UtilComm.encode("1967-08-13")+"</jarq>");
	buffer.append("<jaay>"+UtilComm.encode("4096")+"</jaay>");
	buffer.append("<jafs>"+UtilComm.encode("4096")+"</jafs>");
	buffer.append("<jabdje>"+UtilComm.encode("3.1415")+"</jabdje>");
	buffer.append("<larq>"+UtilComm.encode("1966-08-13")+"</larq>");
	buffer.append("<ajzlx>"+UtilComm.encode("1")+"</ajzlx>");
	buffer.append("<tqxzpc>"+UtilComm.encode("2")+"</tqxzpc>");
	buffer.append("</jaxx>");
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