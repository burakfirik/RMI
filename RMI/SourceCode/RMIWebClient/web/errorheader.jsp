<%
    String msg = (String)request.getAttribute("msg");
    String error = (String)request.getAttribute("error");

    if(msg==null) msg="";
    if(error==null) error="";
%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<%
    if(msg.length()!=0){
%>
<div id="msg"><%=msg%></div>
<% }%>
<%
    if(error.length()!=0){
%>
<div id="error"><%=error%></div>
<% }%>


</body>
</html>