<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ユーザー一覧 (JPA & JSP テスト)</title>
    <style>
        table { border-collapse: collapse; width: 80%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f4f4f4; }
        hr {
            margin: 30px 0;
            border: 0;
            border-top: 1px solid #eee;
        }
        /* ログインステータス用の簡易スタイル */
        .auth-status {
            background-color: #f9f9f9;
            padding: 10px;
            text-align: right;
            border-bottom: 1px solid #ddd;
        }

/* 登録フォームのカードデザイン */
.register-container {
    max-width: 400px;
    margin: 20px 0;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

.register-container legend {
    font-weight: bold;
    color: #333;
    padding: 0 10px;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
    font-size: 0.9em;
    color: #666;
}

.form-group input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box; /* paddingを含めた幅計算 */
}

.btn-submit {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 15px;
    border-radius: 4px;
    cursor: pointer;
    width: 100%;
}

.btn-submit:hover {
    background-color: #0056b3;
}

    </style>
</head>
<body>

    <div class="auth-status">
        <c:choose>
            <c:when test="${not empty sessionScope.currentUser}">
                ようこそ、<strong>${sessionScope.currentUser.username}</strong> さん
                （権限: ${sessionScope.currentUser.role}）
                | <a href="logout">ログアウト</a>
            </c:when>
            <c:otherwise>
                ゲストさん | <a href="login.jsp">ログイン</a>
            </c:otherwise>
        </c:choose>
    </div>

    <h1>ユーザー一覧 (JPA & JSP テスト)</h1>

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

    <c:if test="${empty userBean.allUsers}">
        <p>ユーザーが見つかりません。DBにデータが入っているか確認してください。</p>
    </c:if>

<hr>
<div class="register-container">
    <fieldset style="border: none; padding: 0; margin: 0;">
        <legend>新規ユーザー登録</legend>
        <form action="register" method="post">
            <div class="form-group">
                <label>名前</label>
                <input type="text" name="name" placeholder="例: 山田 太郎" required>
            </div>
            <div class="form-group">
                <label>メールアドレス</label>
                <input type="email" name="email" placeholder="example@mail.com" required>
            </div>
            <div class="form-group">
                <label>パスワード</label>
                <input type="password" name="password" required>
            </div>
            <button type="submit" class="btn-submit">登録する</button>
        </form>
    </fieldset>
</div>
<hr>

</body>
</html>