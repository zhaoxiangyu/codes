<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>exception</title>
	<script language="javascript">
		alert('<%=request.getAttribute("msg")%>');
		history.back();
	</script>
</head>

<body>
</body>
</html>