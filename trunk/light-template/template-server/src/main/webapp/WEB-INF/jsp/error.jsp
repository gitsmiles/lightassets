<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>

<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<html>
<body bgcolor="red">

	<%@ page isErrorPage="true" %>
	The exception <%= exception.getMessage() %> tells me you
	     made a wrong choice. 
   <% if (exception != null) { %>
   <pre>
     <% exception.printStackTrace(new java.io.PrintWriter(out)); %>
     <%
        Logger logger = LoggerFactory.getLogger("error.jsp");
        logger.error(exception.getMessage(),exception);
     %>
   </pre>
   <% } %>
</body>
</html>
