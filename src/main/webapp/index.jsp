<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>JPA & JSP テスト</title>
    <style>
        table { border-collapse: collapse; width: 80%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f4f4f4; }
        hr {
            margin: 30px 0; /* 上下に30pxの隙間を作る */
            border: 0;
            border-top: 1px solid #eee; /* 線の色を少し薄くして上品に */
            }
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

<hr>
<fieldset>
    <legend>新規ユーザー登録</legend>
    <form action="register" method="post">
        名前: <input type="text" name="name" required><br>
        メール: <input type="email" name="email" required><br>
        パスワード: <input type="password" name="password" required>
        <button type="submit">登録する</button>
    </form>
</fieldset>
<hr>

</body>
</html>