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
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 1. レスポンスの種類をHTMLに設定（最初に行う）
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>MySQL JDBC Test</title></head>");
        out.println("<body>");
        out.println("<h1>MySQL JDBC Test Result</h1>");

        try {
            // 2. 環境変数から接続情報を取得
            String url = System.getenv("AI_SENSEI_DB_URL");
            String user = System.getenv("AI_SENSEI_DB_USER");
            String password = System.getenv("AI_SENSEI_DB_PASS");

            out.println("<p>Connecting to: <code>" + url + "</code></p>");

            // 3. データベース接続
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                out.println("<p style='color: green;'>Status: Connected!</p>");
                
                String tableName = "items"; 

                // --- 件数表示 ---
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
                    if (rs.next()) {
                        out.println("<p>Total record count in <b>" + tableName + "</b>: " + rs.getInt(1) + "</p>");
                    }
                }

                out.println("<hr>");

                // --- データ一覧表示（ID降順で5件） ---
                out.println("<h3>Latest 5 Items:</h3>");
                String sql = "SELECT id, name, price FROM " + tableName + " ORDER BY id DESC LIMIT 5";
                
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    
                 // width: 100% を削除し、自動調整または固定幅にします
                    out.println("<table border='1' style='border-collapse: collapse; margin-top: 10px;'>");
                    out.println("<tr style='background-color: #f2f2f2;'>");

                    // 各列の幅を px (ピクセル) で指定
                    out.println("  <th style='padding: 8px; width: 50px;'>ID</th>");
                    out.println("  <th style='padding: 8px; width: 200px;'>Name</th>");
                    out.println("  <th style='padding: 8px; width: 100px;'>Price</th>");
                    out.println("</tr>");

                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td style='padding: 8px;'>" + rs.getInt("id") + "</td>");
                        out.println("<td style='padding: 8px;'>" + rs.getString("name") + "</td>");
                        out.println("<td style='padding: 8px;'>" + String.format("%,d円", rs.getInt("price")) + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
            }
        } catch (Exception e) {
            // エラー表示もHTML形式で
            out.println("<h2 style='color: red;'>!!! Error Occurred !!!</h2>");
            out.println("<pre style='background: #eee; padding: 10px;'>" + e.getMessage() + "</pre>");
            e.printStackTrace(new PrintWriter(out));
        }

        out.println("</body>");
        out.println("</html>");
    }
}