<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ page import="com.atsu.pojo.Employee" %><%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2022/7/28
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page isELIgnored="false" %>
<html>


<head>
    <!--jQuery引入-->
    <script src="<%=application.getAttribute("baseUrl")%>lib/static/js/jquery.js"></script>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <title>员工页面</title>
</head>
<body>
    <div class="container">
<%--        标题--%>
        <div class="row">
            <div class="col-xs-12">
                <h1>员工信息</h1>
            </div>
        </div>

        <div class="row">
            <div class="col-md-offset-8">
                <button class="btn btn-info">新增</button>
                <button class="btn btn-danger">删除</button>
            </div>
        </div>
    <%--    显示表格信息--%>
        <div class="row">
            <div class="col-xs-12">
                <table class="table table-hover">
                    <tr >
                        <td>#</td>
                        <td>员工姓名</td>
                        <td>性别</td>
                        <td>邮箱</td>
                        <td>部门</td>
                        <td>操作</td>
                    </tr>
                    <%PageInfo<Employee> employeePageInfo = (PageInfo<Employee>)request.getAttribute("employeePages");%>
                    <c:forEach var="emp" items="<%=employeePageInfo.getList()%>">
                        <tr >
                            <td>${emp.employeeId}</td>
                            <td>${emp.employeeName}</td>
                            <td>${emp.employeeGender}</td>
                            <td>${emp.employeeEmail}</td>
                            <td>${emp.department.departmentName}</td>
                            <td>
                                <button class="btn btn-primary"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑</button>
                                <button class="btn btn-danger"><span class="glyphicon glyphicon-erase" aria-hidden="true"></span> 删除</button>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>

        </div>
<%--        显示分页信息--%>
        <div class="row">
<%--            显示分页文字信息--%>
            <div class="col-xs-6">
                共有${employeePages.pages}页，${employeePages.total}条记录
            </div>
<%--            显示分页条信息--%>
            <div class="col-xs-6">
                <nav aria-label="Page navigation">
                    <ul class="pagination">

                        <li><a href="${applicationScope.baseUrl}emps/1">首页</a></li>
<%--                        当存在上一页时，才显示上一页的标签--%>
                        <c:if test="${employeePages.hasPreviousPage}">
                            <li>
                                <a href="${applicationScope.baseUrl}emps/${employeePages.prePage}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>

<%--                        展示当前导航栏--%>
                        <c:forEach items="${employeePages.navigatepageNums}" var="pageNum">
                            <c:choose>
                                <c:when test="${pageNum==employeePages.pageNum}">
                                    <li class="active"><a href="${applicationScope.baseUrl}emps/${pageNum}">${pageNum}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="${applicationScope.baseUrl}emps/${pageNum}">${pageNum}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
<%--存在下一页才展示下一页的标签--%>
                        <c:if test="${employeePages.hasNextPage}">
                            <li>
                                <a href="${applicationScope.baseUrl}emps/${employeePages.nextPage}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>
                        <li><a href="${applicationScope.baseUrl}emps/${employeePages.pages}">末页</a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>
