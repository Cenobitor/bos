<%--
  Created by IntelliJ IDEA.
  User: Mac
  Date: 26/03/2018
  Time: 9:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<shiro:authenticated>
    你已经通过认证
</shiro:authenticated><hr/>
<shiro:hasPermission name="courierAction_pageQuery">
    你有courierAction_pageQuery权限
</shiro:hasPermission><hr/>
<shiro:hasRole name="admin">
    你有admin角色
</shiro:hasRole>
</body>
</html>
