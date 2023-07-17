<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Greeting Page</title>
</head>
<body>
<h1>Greeting</h1>
<%
    String name = request.getParameter("name"); // Retrieve the name parameter from the request
    if (name == null || name.isEmpty()) {
        out.println("<p>Please provide a name.</p>");
    } else {
        out.println("<p>Hi, " + name + "!</p>");
    }
%>
</body>
</html>
