<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>JPA & JSP テスト</title>
    <style>
        table { border-collapse: collapse; width: 80%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f4f4f4; }
    </style>
</head>
<body>
    <h1>ユーザー一覧 (JPA 取得)</h1>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>
            <%-- UserBean の getAllUsers() を呼び出す --%>
            <c:forEach var="user" items="${userBean.allUsers}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <%-- データが空だった場合のメッセージ --%>
    <c:if test="${empty userBean.allUsers}">
        <p>ユーザーが見つかりません。DBにデータが入っているか確認してください。</p>
    </c:if>
</body>
</html>