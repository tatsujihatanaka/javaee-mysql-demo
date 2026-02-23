<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ログイン</title>
    <style>
        /* index.jsp で作成した CSS をここにコピー（共通化しても良いですね） */
        fieldset { border: 1px solid #ddd; border-radius: 8px; padding: 20px; width: fit-content; margin: 20px auto; background-color: #fafafa; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: inline-block; width: 100px; }
        button { background-color: #28a745; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
    </style>
</head>
<body>

<fieldset>
    <legend>ログイン</legend>
    <%-- ログイン失敗時のメッセージ表示 --%>
    <% if("1".equals(request.getParameter("error"))) { %>
        <p style="color: red;">メールアドレスまたはパスワードが正しくありません。</p>
    <% } %>

<%-- actionを "login-auth" に指定することで、LoginServlet.java にデータが飛びます --%>
<form action="login-auth" method="post">
    <div class="form-group">
        <label>メール:</label>
        <input type="email" name="email" required>
    </div>
    <div class="form-group">
        <label>パスワード:</label>
        <input type="password" name="password" required>
    </div>
    <button type="submit">ログイン</button>
</form>

    <p><a href="index.jsp">新規登録はこちら</a></p>
</fieldset>

</body>
</html>