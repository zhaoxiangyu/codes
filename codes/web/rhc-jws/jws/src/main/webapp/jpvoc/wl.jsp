<%@ page
	import="java.net.URLEncoder" 
	contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"
%>
<html>
<head>
</head>
<body>
<% String voc = URLEncoder.encode("あキューせいでん（阿Q正伝［专］）阿Q正传","UTF-8"); %>
<a href="/jpvoc/parser.jsp?jpvoc='<%=voc%>'">parser.jsp?jpvoc=あキューせいでん（阿Q正伝［专］）阿Q正传</a>
<br/>
<% voc = URLEncoder.encode("あいかわらず（相変わらず）［副］照旧，依然","UTF-8"); %>
<a href="/jpvoc/parser.jsp?jpvoc='<%=voc%>'">parser.jsp?jpvoc=あいかわらず（相変わらず）［副］照旧，依然</a>
</body>
</html>