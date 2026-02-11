package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/JndiServlet2")
public class JndiServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DIにより、Payaraが管理する接続プールを注入
    @Resource(lookup = "jdbc/mysql")
    private DataSource ds;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>JNDI Connection Test</title></head>");
        out.println("<body>");
        out.println("<h1>MySQL JNDI Test Result</h1>");

        try {
            // サーバーから接続を借りる
            try (Connection conn = ds.getConnection()) {
                out.println("<p style='color: green; font-weight: bold;'>Status: Connected via JNDI!</p>");
                out.println("<p>Database: " + conn.getMetaData().getDatabaseProductName() + "</p>");

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
                    
                    out.println("<table border='1' style='border-collapse: collapse; margin-top: 10px;'>");
                    out.println("<tr style='background-color: #f2f2f2;'>");
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
        } catch (SQLException e) {
            out.println("<h2 style='color: red;'>!!! SQL Error Occurred !!!</h2>");
            out.println("<pre style='background: #eee; padding: 10px;'>" + e.getMessage() + "</pre>");
            e.printStackTrace(out);
        } catch (Exception e) {
            out.println("<h2 style='color: red;'>!!! System Error Occurred !!!</h2>");
            out.println("<pre style='background: #eee; padding: 10px;'>" + e.getMessage() + "</pre>");
            e.printStackTrace(out);
        }

        out.println("</body></html>");
    }
}