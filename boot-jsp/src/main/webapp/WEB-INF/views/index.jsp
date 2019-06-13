<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
hello world </br>
<c:if test="${name != null}">
    ${name} holle
</c:if>

<c:forEach items="${shareChannels}" var="channel">
    ${channel.type} - ${channel.name}
</c:forEach>