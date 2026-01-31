package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/jdbc")
public class JdbcServlet extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter(); // try-with-resourcesの外に出して、エラー時も使えるようにします
        
        try {
            out.println("=== MySQL JDBC Test ===");

            // 1. 環境変数から情報を取得
            String url = System.getenv("AI_SENSEI_DB_URL");
            String user = System.getenv("AI_SENSEI_DB_USER");
            String password = System.getenv("AI_SENSEI_DB_PASS");

            out.println("Connecting to: " + url); // 読み込めているか確認

            // 2. 接続とクエリ実行
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                out.println("Status: Connected!");
                
                String tableName = "items"; 
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
                    if (rs.next()) {
                        out.println("Table '" + tableName + "' count: " + rs.getInt(1));
                    }
                }
            }
        } catch (Exception e) {
            // エラーの詳細をブラウザに表示する
            out.println("\n!!! Error Occurred !!!");
            out.println("Message: " + e.getMessage());
            e.printStackTrace(out); // これでブラウザにスタックトレースが出ます
        }
    }

}