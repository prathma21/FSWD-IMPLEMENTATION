package com.homefinder;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO = Data Access Object. All SQL for the "properties" table lives here.
 * (In Unit 4 this role is played by the "persistence layer".)
 *
 * try-with-resources: Connection, Statement and ResultSet are closed
 * automatically at the end of the try block.
 */
public class PropertyDAO {

    // ---------- READ with Statement + ResultSet (no user input, so Statement is fine) ----------
    public List<Property> findAll() throws SQLException {
        List<Property> list = new ArrayList<>();
        String sql = "SELECT * FROM properties ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {                  // moves to the next row; false when finished
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // ---------- Scalar query: COUNT ----------
    public int count() throws SQLException {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM properties")) {
            rs.next();
            return rs.getInt(1);                 // column number 1
        }
    }

    // ---------- Queries WITH user input -> PreparedStatement ----------
    // Never build SQL by joining strings with user input ("... WHERE id = " + id).
    // That allows SQL injection. Use ? placeholders instead.
    public Property findById(int id) throws SQLException {
        String sql = "SELECT * FROM properties WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);                    // fills the first ?
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;                             // not found
    }

    public List<Property> findByStatusAndMaxPrice(String status, BigDecimal maxPrice) throws SQLException {
        List<Property> list = new ArrayList<>();
        String sql = "SELECT * FROM properties WHERE status = ? AND price <= ? ORDER BY price";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setBigDecimal(2, maxPrice);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    // ---------- JOIN query: reading columns by NAME from a ResultSet ----------
    public void printPropertiesWithAgents() throws SQLException {
        String sql = "SELECT p.title, p.price, u.name AS agent " +
                     "FROM properties p JOIN users u ON p.agent_id = u.id ORDER BY p.id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("%-26s %12s   Agent: %s%n",
                        rs.getString("title"), rs.getBigDecimal("price"), rs.getString("agent"));
            }
        }
    }

    // ---------- INSERT ----------
    // executeUpdate() is used for INSERT, UPDATE, DELETE. It returns the number of rows changed.
    public void insert(Property p) throws SQLException {
        String sql = "INSERT INTO properties (title, price, location, bedrooms, bathrooms, area, type, status, image_url, description, agent_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTitle());
            ps.setBigDecimal(2, p.getPrice());
            ps.setString(3, p.getLocation());
            ps.setInt(4, p.getBedrooms());
            ps.setInt(5, p.getBathrooms());
            ps.setInt(6, p.getArea());
            ps.setString(7, p.getType());
            ps.setString(8, p.getStatus());
            ps.setString(9, p.getImageUrl());
            ps.setString(10, p.getDescription());
            ps.setInt(11, p.getAgentId());
            ps.executeUpdate();

            // MySQL created the id (AUTO_INCREMENT). Read it back.
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getInt(1));
                }
            }
        }
    }

    // ---------- UPDATE ----------
    public int updatePrice(int id, BigDecimal newPrice) throws SQLException {
        String sql = "UPDATE properties SET price = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, newPrice);
            ps.setInt(2, id);
            return ps.executeUpdate();           // rows changed (0 = id not found)
        }
    }

    // ---------- DELETE ----------
    // Note: a property that has inquiries or favorites cannot be deleted
    // (foreign keys protect them). Try deleting one you added yourself.
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM properties WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    // ---------- helper: one ResultSet row -> one Property object ----------
    private Property mapRow(ResultSet rs) throws SQLException {
        Property p = new Property();
        p.setId(rs.getInt("id"));
        p.setTitle(rs.getString("title"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setLocation(rs.getString("location"));
        p.setBedrooms(rs.getInt("bedrooms"));
        p.setBathrooms(rs.getInt("bathrooms"));
        p.setArea(rs.getInt("area"));
        p.setType(rs.getString("type"));
        p.setStatus(rs.getString("status"));
        p.setImageUrl(rs.getString("image_url"));
        p.setDescription(rs.getString("description"));
        p.setAgentId(rs.getInt("agent_id"));
        return p;
    }
}
