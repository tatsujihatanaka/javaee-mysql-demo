package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

// URLもクラス名に合わせて /JndiServlet にしておくと分かりやすいです
@WebServlet("/JndiServlet")
public class JndiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body><h2>JNDI Connection Test</h2>");
        
        try {
            // 1. JNDIの初期コンテキストを取得
            Context initContext = new InitialContext();
            
            // 2. ルックアップ
            Context envContext = (Context) initContext.lookup("java:comp/env");
            
            // 3. context.xmlで定義した名前 "jdbc/mysql" でDataSourceを取得
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            
            // 4. データベース接続を取得
            try (Connection conn = ds.getConnection()) {
                out.println("<p style='color:green; font-weight:bold;'>Connection Success!</p>");
                out.println("<p>Database: " + conn.getMetaData().getDatabaseProductName() + "</p>");
            }
            
        } catch (NamingException e) {
            out.println("<p style='color:red;'>JNDI Lookup Failed...</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        } catch (SQLException e) {
            out.println("<p style='color:red;'>SQL Connection Failed...</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
        
        out.println("</body></html>");
    }
}