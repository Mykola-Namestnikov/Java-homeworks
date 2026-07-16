package org.example.database;

import org.flywaydb.core.Flyway;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final Database INSTANCE = new Database();
    private Connection connection;

    private static final String DB_URL = "jdbc:h2:./testdb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private Database() {
        try {
            flywayMigration(DB_URL, DB_USER, DB_PASSWORD);
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Помилка підключення до БД", e);
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

    private void flywayMigration(String url, String user, String password) {
        Flyway flyway = Flyway.configure().dataSource(url, user, password).baselineOnMigrate(true).load();
        flyway.migrate();
    }
}
