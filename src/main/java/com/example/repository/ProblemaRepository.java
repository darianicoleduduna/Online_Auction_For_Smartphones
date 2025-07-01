package com.example.repository;

import com.example.model.Problema;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProblemaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProblemaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Problema> findByUtilizatorID(int utilizatorID) {
        String sql = "SELECT p.ProblemaID, p.Descriere, p.Status_pb, a.Nume AS AdminNume, a.Prenume AS AdminPrenume " +
                "FROM Probleme p " +
                "JOIN Admini a ON p.AdminID = a.AdminID " +
                "WHERE p.UtilizatorID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Problema problema = new Problema();
            problema.setProblemaID(rs.getInt("ProblemaID"));
            problema.setDescriere(rs.getString("Descriere"));
            problema.setStatus(rs.getString("Status_pb"));
            problema.setAdminNume(rs.getString("AdminNume"));
            problema.setAdminPrenume(rs.getString("AdminPrenume"));
            return problema;
        }, utilizatorID);
    }

    public void save(Problema problema) {
        String sql = "INSERT INTO Probleme (Descriere, Status_pb, UtilizatorID, AdminID) VALUES (?, 'activa', ?, ?)";
        int adminID = getRandomAdminID();
        jdbcTemplate.update(sql, problema.getDescriere(), problema.getUtilizatorID(), adminID);
    }

    public void update(int problemaID, Problema problema) {
        String sql = "UPDATE Probleme SET Descriere = ? WHERE ProblemaID = ? AND Status_pb = 'activa'";
        jdbcTemplate.update(sql, problema.getDescriere(), problemaID);
    }

    public void delete(int problemaID) {
        String sql = "DELETE FROM Probleme WHERE ProblemaID = ?";
        jdbcTemplate.update(sql, problemaID);
    }

    private int getRandomAdminID() {
        String sql = "SELECT AdminID FROM Admini ORDER BY NEWID() OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    public Problema getProblemaById(int id) {
        String sql = "SELECT * FROM Probleme WHERE ProblemaID = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Problema problema = new Problema();
            problema.setProblemaID(rs.getInt("ProblemaID"));
            problema.setDescriere(rs.getString("Descriere"));
            problema.setUtilizatorID(rs.getInt("UtilizatorID"));
            problema.setAdminID(rs.getInt("AdminID"));
            problema.setStatus(rs.getString("Status_pb"));
            return problema;
        }, id);
    }
}
