package org.example.service;

import org.example.database.Database;
import org.example.dto.*;
import org.example.util.SqlFileReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    public List<MaxProjectCountClient> findMaxProjectsClient() {
        List<MaxProjectCountClient> result = new ArrayList<>();
        String sql = SqlFileReader.readSqlFile("sql/find_max_projects_client.sql");

        Connection conn = Database.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("NAME");
                int count = rs.getInt("PROJECT_COUNT");
                result.add(new MaxProjectCountClient(name, count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<LongestProject> findLongestProject() {
        List<LongestProject> result = new ArrayList<>();
        String sql = SqlFileReader.readSqlFile("sql/find_longest_project.sql");

        Connection conn = Database.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                result.add(new LongestProject(
                        rs.getString("NAME"),
                        rs.getInt("MONTH_COUNT")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker() {
        List<MaxSalaryWorker> result = new ArrayList<>();
        String sql = SqlFileReader.readSqlFile("sql/find_max_salary_worker.sql");

        Connection conn = Database.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                result.add(new MaxSalaryWorker(
                        rs.getString("NAME"),
                        rs.getInt("SALARY")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<YoungestEldestWorker> findYoungestEldestWorkers() {
        List<YoungestEldestWorker> result = new ArrayList<>();
        String sql = SqlFileReader.readSqlFile("sql/find_youngest_eldest_workers.sql");

        Connection conn = Database.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                result.add(new YoungestEldestWorker(
                        rs.getString("TYPE"),
                        rs.getString("NAME"),
                        rs.getString("BIRTHDAY")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<ProjectPrice> printProjectPrices() {
        List<ProjectPrice> result = new ArrayList<>();
        String sql = SqlFileReader.readSqlFile("sql/print_project_prices.sql");

        Connection conn = Database.getInstance().getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                result.add(new ProjectPrice(
                        rs.getString("NAME"),
                        rs.getLong("PRICE")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
