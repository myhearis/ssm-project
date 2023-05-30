<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="<%=application.getAttribute("baseUrl")%>lib/static/bootstrap-3.4.1-dist/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
<script type="text/javascript" href="<%=application.getAttribute("baseUrl")%>lib/static/bootstrap-3.4.1-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=application.getAttribute("baseUrl")%>lib/static/js/jquery.js"></script>
<body>
<input type="button" value="按钮" class="">
<h2>Hello World!</h2>
<%response.sendRedirect(application.getAttribute("baseUrl")+"emps/1");%>
</body>
</html>
