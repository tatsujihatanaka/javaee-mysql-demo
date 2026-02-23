package com.example.demo;

import java.io.IOException;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.PasswordHash; // 追加
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.entity.User;

/**
 * 独自セッション管理によるログインサーブレット
 */
@WebServlet("/login-auth")
public class LoginServlet extends HttpServlet {

    @Inject
    private UserBean userBean;

    @Inject
    private PasswordHash passwordHash; // 追加：ハッシュ検証用

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. フォームパラメータの取得
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // --- デバッグログ ---
        System.out.println("========== Login Debug Start ==========");
        System.out.println("Input Email: [" + email + "]");

        // 2. ユーザーをDBから検索
        User user = userBean.findByEmail(email);

        boolean isSuccess = false;
        if (user == null) {
            System.out.println("Result: User NOT FOUND");
        } else {
            System.out.println("Result: User FOUND");
            // passwordHash.verify を使って検証
            isSuccess = passwordHash.verify(password.toCharArray(), user.getPassword());
            System.out.println("Password Verify Result: " + isSuccess);
        }
        System.out.println("========== Login Debug End ===========");

        // 3. 認証判定
        if (isSuccess) {
            // ログイン成功：セッションを開始し、ユーザー情報を格納
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", user);
            
            // index.jspへリダイレクト
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            
        } else {
            // ログイン失敗：エラーパラメータを付けてlogin.jspに戻す
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=1");
        }
    }
}