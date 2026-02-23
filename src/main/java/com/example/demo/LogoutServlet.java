package com.example.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // セッションを無効化（全データを破棄）
        request.getSession().invalidate();
        
        // ログイン画面かトップ画面にリダイレクト
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
