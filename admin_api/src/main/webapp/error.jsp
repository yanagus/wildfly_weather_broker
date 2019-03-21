<%
   String message = pageContext.getException().getMessage();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Exception</title>
</head>
<body>
<h2>Exception occurred while processing the request</h2>
<p>Message: <%= message%></p>
</body>
</html>