package com.example.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class LicitatiiRepository {

    private final JdbcTemplate jdbcTemplate;

    public LicitatiiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> findLicitatieDetails(int masaID) {
        String sql = """
            SELECT t.TelefonID, t.Descriere, t.Culoare, t.Memorie, c.Brand, v.Nume, v.Prenume, v.Rating,t.poza_url,t.Anfabricatie,t.Status_stoc
            FROM Masa_licitatie m
            JOIN Telefoane t ON m.TelefonID = t.TelefonID
            JOIN Categorii c ON t.CategorieID = c.CategorieID
            JOIN Vanzatori v ON t.VanzatorID = v.VanzatorID
            WHERE m.MasaID = ?
        """;
        return jdbcTemplate.queryForMap(sql, masaID);
    }

    public List<Map<String, Object>> findOferteForMasa(int masaID) {
        String sql = """
        SELECT o.Nr_oferta, o.Suma_oferita, u.Username
        FROM Masa_licitatieCumparatori o
        JOIN Cumparatori c ON o.CumparatorID = c.CumparatorID
        JOIN Utilizatori u ON c.UtilizatorID = u.UtilizatorID
        WHERE o.MasaID = ?
        ORDER BY o.Nr_oferta DESC
    """;
        return jdbcTemplate.queryForList(sql, masaID);
    }

    public void saveOferta(int masaID, double suma, String username) {
        String sql = """
        INSERT INTO Masa_licitatieCumparatori (MasaID, CumparatorID, Suma_oferita, Nr_oferta)
        VALUES (?, 
            (SELECT UtilizatorID FROM Utilizatori WHERE Username = ?), 
            ?, 
            (SELECT COALESCE(MAX(Nr_oferta), 0) + 1 FROM Masa_licitatieCumparatori WHERE MasaID = ?)
        )
    """;
        jdbcTemplate.update(sql, masaID, username, suma, masaID);
    }

}

