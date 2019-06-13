<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
hello world </br>
<c:if test="${name != null}">
    ${name} holle
</c:if>

<c:forEach items="${EnumShareChannel.values}" var="channel">
    ${channel}
</c:forEach>