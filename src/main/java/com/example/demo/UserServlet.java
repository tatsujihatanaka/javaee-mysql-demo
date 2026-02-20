package com.example.demo;

import java.io.IOException;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.entity.User;

@WebServlet("/register")
public class UserServlet extends HttpServlet {

    @Inject
    private UserBean userBean;

    @Inject
    private PasswordHash passwordHash; // ★SpringのPasswordEncoderのように使えます
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 文字化け対策
        request.setCharacterEncoding("UTF-8");

        // 1. フォームからの値を受け取る
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        String rawPassword = request.getParameter("password"); // ★追加

     // 2. パスワードを強力にハッシュ化する
        // 内部でソルトの付与とストレッチングが自動で行われます
        String encodedPassword = passwordHash.generate(rawPassword.toCharArray());

        // 2. 保存用のエンティティを作成
        User user = new User();
        user.setUsername(name); // Entityのフィールド名に合わせる
        user.setEmail(email);
        user.setRole("USER");   // 今回は固定値でUSERを設定
        user.setPassword(encodedPassword); // ★ハッシュ化した値をセット

        // 3. UserBeanを通じてDBに保存
        userBean.setNewUser(user);
        userBean.save();

        // 4. 一覧画面へ戻る（二重送信防止のためリダイレクト）
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}