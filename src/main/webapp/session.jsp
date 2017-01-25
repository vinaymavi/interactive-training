<%--
  Created by IntelliJ IDEA.
  User: vku131
  Date: 1/23/17
  Time: 7:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/lib/reveal.css">
    <link rel="stylesheet" href="css/lib/theme/black.css">
    <title><c:out value="${presentation.name}"/></title>
</head>
<body>
<div class="reveal">
    <div class="slides">
        <c:forEach items="${slides}" var="slide">
            <section><c:out value="${slide.html}" escapeXml="false"/></section>
        </c:forEach>
    </div>
</div>
<script src="js/lib/reveal.js"></script>
<script>
    Reveal.initialize();
</script>
</body>
</html>
