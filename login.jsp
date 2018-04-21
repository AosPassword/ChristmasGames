<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/14
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>

<body style="text-align: center;">
<form action="/LogInServlet" method="post">
    <table width="60%" border="1">
        <tr>
            <td>用户名</td>
            <td>
                <%--使用EL表达式${}提取存储在request对象中的formbean对象中封装的表单数据(formbean.userName)以及错误提示消息(formbean.errors.userName)--%>
                <input type="text" name="openid">
            </td>
        </tr>
        <tr>
            <td>
                <input type="reset" value="清空">
            </td>
            <td>
                <input type="submit" value="登陆">
            </td>
        </tr>
    </table>
</form>
</body>

</html>
