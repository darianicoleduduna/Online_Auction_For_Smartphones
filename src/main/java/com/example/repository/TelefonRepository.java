
    package com.example.repository;

import com.example.model.Telefon;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

    @Repository
    public class TelefonRepository {

        private final JdbcTemplate jdbcTemplate;

        public TelefonRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        // Găsește toate telefoanele
        public List<Telefon> findAll() {
            String sql = "SELECT TelefonID, Descriere,Anfabricatie,Culoare,Memorie,Status_stoc FROM Telefoane";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Telefon telefon = new Telefon();
                telefon.setTelefonId(rs.getInt("TelefonID")); // TelefonId este int, conform clasei existente
                telefon.setDescriere(rs.getString("Descriere"));
                telefon.setAnFabricatie(rs.getInt("Anfabricatie"));
                telefon.setCuloare(rs.getString("Culoare"));
                telefon.setMemorie(rs.getString("Memorie"));
                telefon.setStatusStoc(rs.getString("Status_stoc"));
                return telefon;
            });
        }

        // Găsește un telefon după ID
        public Telefon findById(int id) {
            String sql = "SELECT TelefonID, Descriere,Anfabricatie,Culoare,Memorie,Status_stoc FROM Telefoane WHERE TelefonID = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Telefon telefon = new Telefon();
                telefon.setTelefonId(rs.getInt("TelefonID"));
                telefon.setDescriere(rs.getString("Descriere"));
                telefon.setAnFabricatie(rs.getInt("Anfabricatie"));
                telefon.setCuloare(rs.getString("Culoare"));
                telefon.setMemorie(rs.getString("Memorie"));
                telefon.setStatusStoc(rs.getString("Status_stoc"));
                return telefon;
            }, id);
        }

        public void save(Telefon telefon) {
            String sql = "INSERT INTO Telefoane (Descriere,Anfabricatie,Culoare,Memorie,Status_stoc) VALUES (?, ?, ?,?,?)";
            jdbcTemplate.update(sql, telefon.getDescriere(), telefon.getAnFabricatie(),telefon.getCuloare(), telefon.getMemorie(), telefon.getStatusStoc());
        }

        public void update(Telefon telefon) {
            String sql = "UPDATE Telefoane SET Descriere = ?, Anfabricatie = ?, Culoare = ?, Memorie = ?, Status_stoc = ? WHERE TelefonID = ?";
            jdbcTemplate.update(sql, telefon.getDescriere(), telefon.getAnFabricatie(), telefon.getCuloare(), telefon.getMemorie(), telefon.getStatusStoc(), telefon.getTelefonId());
        }

        public void deleteById(Long id) {
            String sql = "DELETE FROM Telefoane WHERE TelefonID= ?";
            jdbcTemplate.update(sql, id);
        }
        // Găsește telefoanele de la vânzătorii de top
        public List<Telefon> findPhonesByTopSellers() {
            String sql = """
        SELECT t.TelefonID, t.Descriere, t.Anfabricatie, t.Culoare, t.Memorie, t.Status_stoc
        FROM Telefoane t
        WHERE t.VanzatorID IN (
            SELECT v.VanzatorID
            FROM Vanzatori v
            WHERE v.Rating > (SELECT AVG(Rating) FROM Vanzatori)
        )
    """;
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Telefon telefon = new Telefon();
                telefon.setTelefonId(rs.getInt("TelefonID"));
                telefon.setDescriere(rs.getString("Descriere"));
                telefon.setAnFabricatie(rs.getInt("Anfabricatie"));
                telefon.setCuloare(rs.getString("Culoare"));
                telefon.setMemorie(rs.getString("Memorie"));
                telefon.setStatusStoc(rs.getString("Status_stoc"));
                return telefon;
            });
        }
        public List<Telefon> findPhonesByCategory(String brand) {
            String sql = """
        SELECT t.TelefonID, t.Descriere, t.AnFabricatie, t.Culoare, t.Memorie, t.Status_Stoc
        FROM Telefoane t
        JOIN Categorii c ON t.CategorieID = c.CategorieID
        WHERE c.Brand = ?
    """;
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Telefon telefon = new Telefon();
                telefon.setTelefonId(rs.getInt("TelefonID"));
                telefon.setDescriere(rs.getString("Descriere"));
                telefon.setAnFabricatie(rs.getInt("AnFabricatie"));
                telefon.setCuloare(rs.getString("Culoare"));
                telefon.setMemorie(rs.getString("Memorie"));
                telefon.setStatusStoc(rs.getString("Status_Stoc"));
                return telefon;
            }, brand);
        }

        public List<String> getAllBrands() {
            String sql = """
        SELECT DISTINCT Brand
        FROM Categorii
    """;
            return jdbcTemplate.queryForList(sql, String.class);
        }



    }


