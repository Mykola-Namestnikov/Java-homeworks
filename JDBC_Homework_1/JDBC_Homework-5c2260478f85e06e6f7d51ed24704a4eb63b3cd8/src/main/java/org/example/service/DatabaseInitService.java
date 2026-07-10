package org.example.service;

import org.example.database.Database;
import org.example.util.SqlFileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitService {
    public static void main(String[] args) {
        String sql = SqlFileReader.readSqlFile("sql/init_db.sql");

        Connection conn = Database.getInstance().getConnection();

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблиці успішно створено");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
