package com.example.repository;

import com.example.model.MasaLicitatie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MasaLicitatieRepository {

    private final JdbcTemplate jdbcTemplate;

    public MasaLicitatieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MasaLicitatie> findAll() {
        String sql = "SELECT * FROM Masa_licitatie"; // Interogare SQL
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MasaLicitatie masa = new MasaLicitatie();
            masa.setMasaID(rs.getInt("MasaID"));
            masa.setStartTimp(rs.getTimestamp("Start_timp").toLocalDateTime());
            masa.setStopTimp(rs.getTimestamp("Stop_timp").toLocalDateTime());
            masa.setMinOferta(rs.getInt("Min_oferta"));
            masa.setMaxOferta(rs.getInt("Max_oferta"));
            masa.setCastigator(rs.getInt("Castigator"));
            masa.setTelefonID(rs.getInt("TelefonID"));
            masa.setAdminID(rs.getInt("AdminID"));
            return masa;
        });
    }

    public MasaLicitatie findById(int masaID) {
        String sql = "SELECT * FROM Masa_licitatie WHERE MasaID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{masaID}, (rs, rowNum) -> {
            MasaLicitatie masa = new MasaLicitatie();
            masa.setMasaID(rs.getInt("MasaID"));
            masa.setStartTimp(rs.getTimestamp("Start_timp").toLocalDateTime());
            masa.setStopTimp(rs.getTimestamp("Stop_timp").toLocalDateTime());
            masa.setMinOferta(rs.getInt("Min_oferta"));
            masa.setMaxOferta(rs.getInt("Max_oferta"));
            masa.setCastigator(rs.getInt("Castigator"));
            masa.setTelefonID(rs.getInt("TelefonID"));
            masa.setAdminID(rs.getInt("AdminID"));
            return masa;
        });
    }
    public List<MasaLicitatie> findActiveLicitatiiWithPhones() {
        String sql = """
        SELECT m.MasaID, t.Descriere AS Model, t.poza_url, m.Start_timp, m.Stop_timp, m.Min_oferta 
        FROM Masa_licitatie m 
        JOIN Telefoane t ON m.TelefonID = t.TelefonID
        WHERE m.Stop_timp > CURRENT_TIMESTAMP
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MasaLicitatie masa = new MasaLicitatie();
            masa.setMasaID(rs.getInt("MasaID"));
            masa.setStartTimp(rs.getTimestamp("Start_timp").toLocalDateTime());
            masa.setStopTimp(rs.getTimestamp("Stop_timp").toLocalDateTime());
            masa.setMinOferta(rs.getInt("Min_oferta"));
            masa.setModelTelefon(rs.getString("Model"));
            masa.setPozaUrl(rs.getString("poza_url")); // Adaugă poza URL
            return masa;
        });
    }


}
