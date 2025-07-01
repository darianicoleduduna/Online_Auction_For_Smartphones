package com.example.repository;

import com.example.model.Telefon;
import com.example.model.Vanzator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StatisticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public StatisticsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // selectam telefoanele care urmeaza sa fie scoase la licitatie
    public List<Telefon> findAllPhones() {
        String sql = """
            SELECT t.TelefonID, t.Descriere 
            FROM Telefoane t
            WHERE t.Status_Stoc='disponibil' and t.TelefonID not in(
                select t2.TelefonID 
                from Telefoane t2 join Masa_licitatie m on t2.TelefonID = m.TelefonID
        )
        
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Telefon telefon = new Telefon();
            telefon.setTelefonId(rs.getInt("TelefonID"));
            telefon.setModel(rs.getString("Descriere"));
            //telefon.setStatusLicitat(rs.getString("statusLicitat"));
            return telefon;
        });
    }

    // Categorii disponibile
    public List<Map<String, Object>> findPhoneCategories() {
        String sql = """
            SELECT c.Brand, COUNT(t.TelefonID) AS numarTelefoane
            FROM Categorii c
            LEFT JOIN Telefoane t ON c.CategorieID = t.CategorieID
            GROUP BY c.Brand
        """;
        return jdbcTemplate.queryForList(sql);
    }

    // Top vânzători și telefoanele lor
    public List<Vanzator> findTopVanzatori() {
        String sql = """
            SELECT top 5 v.VanzatorID, v.Nume, v.Prenume, v.Rating
            FROM Vanzatori v
            WHERE v.Rating IS NOT NULL
            ORDER BY v.Rating DESC
            
        """;
        List<Vanzator> vanzatori = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vanzator vanzator = new Vanzator();
            vanzator.setVanzatorId(rs.getInt("VanzatorID"));
            vanzator.setNume(rs.getString("Nume"));
            vanzator.setPrenume(rs.getString("Prenume"));
            vanzator.setRating(rs.getFloat("Rating"));
            return vanzator;
        });

        // Adaugă telefoanele pentru fiecare vânzător
        for (Vanzator vanzator : vanzatori) {
            String telefoaneSql = """
                SELECT c.Model
                FROM Telefoane t join Categorii c on c.CategorieID = t.CategorieID
                WHERE t.VanzatorID = ?
            """;
            List<String> telefoane = jdbcTemplate.queryForList(telefoaneSql, String.class, vanzator.getVanzatorId());
            vanzator.setTelefoane(telefoane);
        }
        return vanzatori;
    }

    // Ultimele telefoane vândute
    public List<Map<String, Object>> findRecentlySoldPhones() {
        String sql = """
            SELECT top 5 c.Model, tr.Suma, tr.Data
            FROM Tranzactii tr
            JOIN Telefoane t ON tr.TelefonID = t.TelefonID
            Join Categorii c on t.CategorieID = c.CategorieID
            ORDER BY tr.Data DESC
            
        """;
        return jdbcTemplate.queryForList(sql);
    }
    public List<Map<String, Object>> getVanzatoriCuBranduriPopulare() {
        String sql = """
        SELECT v.VanzatorID, v.Nume, v.Prenume, 
               STRING_AGG(c.Brand, ', ') AS Branduri, 
               COUNT(t.TelefonID) AS TotalTelefoaneVandute
        FROM Vanzatori v
        JOIN Telefoane t ON v.VanzatorID = t.VanzatorID
        JOIN Categorii c ON t.CategorieID = c.CategorieID
        WHERE t.CategorieID IN (
            SELECT TOP 3 CategorieID
            FROM Telefoane
            GROUP BY CategorieID
            ORDER BY COUNT(*) DESC
        )
        GROUP BY v.VanzatorID, v.Nume, v.Prenume
        ORDER BY TotalTelefoaneVandute DESC
    """;
        return jdbcTemplate.queryForList(sql);
    }
    public List<Map<String, Object>> findVanzatoriWithAvgTransactionBelow(double maxValue) {
        String sql = """
            SELECT 
                v.VanzatorID,
                v.Nume,
                v.Prenume,
                AVG(tr.Suma) AS PretMediu
            FROM 
                Vanzatori v
            JOIN 
                Telefoane t ON v.VanzatorID = t.VanzatorID
            JOIN 
                Tranzactii tr ON t.TelefonID = tr.TelefonID
            WHERE 
                t.CategorieID IN (
                    SELECT 
                        c1.CategorieID
                    FROM 
                        Categorii c1
                    JOIN 
                        Telefoane t1 ON c1.CategorieID = t1.CategorieID
                    JOIN 
                        Tranzactii tr1 ON t1.TelefonID = tr1.TelefonID
                    GROUP BY 
                        c1.CategorieID
                    HAVING 
                        COUNT(t1.TelefonID) >= (
                            SELECT 
                                AVG(NumarTelefoane)
                            FROM (
                                SELECT 
                                    c2.CategorieID,
                                    COUNT(t2.TelefonID) AS NumarTelefoane
                                FROM 
                                    Categorii c2
                                JOIN 
                                    Telefoane t2 ON c2.CategorieID = t2.CategorieID
                                JOIN 
                                    Tranzactii tr2 ON t2.TelefonID = tr2.TelefonID
                                GROUP BY 
                                    c2.CategorieID
                            ) SubCategorii
                        )
                )
            GROUP BY 
                v.VanzatorID, v.Nume, v.Prenume
            HAVING 
                AVG(tr.Suma) < ?
            ORDER BY 
                PretMediu ASC;
        """;

        return jdbcTemplate.queryForList(sql, maxValue);
    }
}
