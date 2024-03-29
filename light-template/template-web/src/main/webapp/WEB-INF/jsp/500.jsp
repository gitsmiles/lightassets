<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>页面访问出错</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="<c:url value="/WEB-INF/js/jquery-1.3.2.min.js"/>" type="text/javascript"></script>
	</head>
	<%
	Throwable ex = null;
	if(exception!=null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Exception)request.getAttribute("javax.servlet.error.exception");
	%>
	<body>
		<div id="content">
				<div>
					<h3>
						页面访问时发生错误:
						<%
							if(ex!=null)
								out.println(ex.getMessage());
						%>
					</h3>
				</div>
				<div>
					<button onclick="history.back();">返回</button> <button onclick="$('#detail_error_msg').toggle();">显示详细信息</button></div>
				</div>

				<div >
				<div id="detail_error_msg" style="display: none">
					<% if (ex != null) { %>
					<pre>
						<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
			      <%
			         Logger logger = LoggerFactory.getLogger("500.jsp");
			         logger.error(exception.getMessage(),exception);
			      %>						
					</pre>
					<% } %>
				</div>
			</div>
	</body>
</html>