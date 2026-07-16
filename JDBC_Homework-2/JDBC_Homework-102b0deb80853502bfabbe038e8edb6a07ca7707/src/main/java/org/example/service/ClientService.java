package org.example.service;

import org.example.database.Database;
import org.example.dto.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final Connection conn;

    public ClientService() {
        this.conn = Database.getInstance().getConnection();
    }

    public long create(String name) {
        validateName(name);
        String sql = "INSERT INTO client (name) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Failed to retrieve ID for the created client.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while creating client", e);
        }
    }

    public String getById(long id) {
        validateId(id);
        String sql = "SELECT name FROM client WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                } else {
                    throw new IllegalArgumentException("Client with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while retrieving client by ID", e);
        }
    }

    public void setName(long id, String name) {
        validateId(id);
        validateName(name);
        String sql = "UPDATE client SET name = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setLong(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new IllegalArgumentException("Update failed. Client with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while updating client name", e);
        }
    }

    public void deleteById(long id) {
        validateId(id);
        String sql = "DELETE FROM client WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new IllegalArgumentException("Deletion failed. Client with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while deleting client", e);
        }
    }

    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, name FROM client";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                clients.add(new Client(rs.getLong("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while retrieving all clients", e);
        }
        return clients;
    }

    private void validateName(String name) {
        if (name == null || name.trim().length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Client name length must be between 2 and 1000 characters.");
        }
    }

    private void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }
    }
}
