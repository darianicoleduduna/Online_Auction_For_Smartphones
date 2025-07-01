package com.example.repository;

import com.example.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT UtilizatorID, Username, Email, Parola FROM Utilizatori WHERE Username = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getInt("UtilizatorID"));
            u.setUsername(rs.getString("Username"));
            u.setEmail(rs.getString("Email"));
            u.setPassword(rs.getString("Parola"));
            return u;
        });
        return Optional.ofNullable(user);
    }
    public void addCumparator(int userId) {
        String sql = "INSERT INTO Cumparatori (UtilizatorID) VALUES (?)";
        jdbcTemplate.update(sql, userId);
    }
    public void addVanzator(int userId) {
        String sql = """
        INSERT INTO Vanzatori (UtilizatorID, Nume, Prenume, Strada, Localitate, Nr)
        SELECT c.UtilizatorID, c.Nume, c.Prenume, c.Strada, c.Localitate, c.Nr
        FROM Cumparatori c
        WHERE c.UtilizatorID = ?
    """;
        jdbcTemplate.update(sql, userId);
    }
    public Map<String, Object> getCumparatorDetails(int userId) {
        String sql = "SELECT * FROM Cumparatori WHERE UtilizatorID = ?";
        return jdbcTemplate.queryForMap(sql, userId);
    }
    public Map<String, Object> getVanzatorDetails(int vanzatorId) {
        String sql = "SELECT * FROM Vanzatori WHERE VanzatorID = ?";
        try {
            return jdbcTemplate.queryForMap(sql, vanzatorId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }




    public Optional<User> findByUsernameWithRoles(String username) {
        String sql = """
        SELECT 
            u.Username, u.Email, u.Parola, u.UtilizatorID,
            c.Strada, c.Localitate, c.Nume, c.Prenume,
            c.CumparatorID AS isCumparator,
            v.VanzatorID AS isVanzator
        FROM 
            Utilizatori u
        LEFT JOIN 
            Cumparatori c ON u.UtilizatorID = c.UtilizatorID
        LEFT JOIN 
            Vanzatori v ON u.UtilizatorID = v.UtilizatorID
        WHERE 
            u.Username = ?
    """;

        List<User> users = jdbcTemplate.query(sql, new Object[]{username}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("UtilizatorID"));
            user.setUsername(rs.getString("Username"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("Parola"));
            user.setStrada(rs.getString("Strada"));
            user.setLocalitate(rs.getString("Localitate"));
            user.setNume(rs.getString("Nume"));
            user.setPrenume(rs.getString("Prenume"));
            user.setCumparator(rs.getObject("isCumparator") != null); // Verificăm dacă există în tabelul Cumparatori
            user.setVanzator(rs.getObject("isVanzator") != null);     // Verificăm dacă există în tabelul Vanzatori
            return user;
        });

        // Returnăm primul element din listă, dacă există
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public boolean isCumparator(int userId) {
        String sql = "SELECT COUNT(*) FROM Cumparatori WHERE UtilizatorID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }
    public boolean isVanzator(int userId) {
        String sql = "SELECT COUNT(*) FROM Vanzatori WHERE UtilizatorID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }
    public void updateUserDetails(int userId, String email, String password) {
        String sql = "UPDATE Utilizatori SET Email = ?, Parola = ? WHERE UtilizatorID = ?";
        try {
            jdbcTemplate.update(sql, email, password, userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Eroare la actualizarea detaliilor utilizatorului", e);
        }
    }
    public void updateCumparatorDetails(int userId, String nume, String prenume, String localitate,
                                        String strada, int nr, String sex, String dataNasterii, int nrOferte) {
        String sql = """
        UPDATE Cumparatori 
        SET Nume = ?, Prenume = ?, Localitate = ?, Strada = ?, Nr = ?, 
            Sex = ?, Data_Nasterii = ?, Nr_oferte = ?
        WHERE UtilizatorID = ?
    """;
        try {
            jdbcTemplate.update(sql, nume, prenume, localitate, strada, nr, sex, dataNasterii, nrOferte, userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Eroare la actualizarea detaliilor cumpărătorului", e);
        }
    }
    public Optional<Integer> findIdByUsername(String username) {
        String sql = "SELECT UtilizatorID FROM Utilizatori WHERE Username = ?";
        return jdbcTemplate.query(sql, rs -> rs.next() ? Optional.of(rs.getInt("UtilizatorID")) : Optional.empty(), username);
    }




}
