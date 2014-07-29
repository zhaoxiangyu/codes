<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/dictaglib" prefix="acc" %>
<%@ page import="java.util.*" %>
<%@ page import="com.acc.framework.constants.Constants" %>
<%
 	Set<String> setUserResources= null;//(HashSet<String>)session.getAttribute(Constants.USER_RESOURCES);
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
