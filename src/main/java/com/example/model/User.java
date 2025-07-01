package com.example.model;

public class User {

    private int id;
    private String username; // Numele utilizatorului
    private String email; // Email-ul utilizatorului
    private String password; // Parola utilizatorului
    private String strada;
    private String localitate;
    private String telefon;
    private String nume;
    private String prenume;
    private boolean isCumparator;
    private boolean isVanzator;
    // Constructori, Getters și Setters

    public User() {
    }

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getter și setter pentru id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter și setter pentru username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter și setter pentru email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter și setter pentru parola
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public boolean isVanzator() {
        return isVanzator;
    }

    public void setVanzator(boolean vanzator) {
        isVanzator = vanzator;
    }

    public boolean isCumparator() {
        return isCumparator;
    }

    public void setCumparator(boolean cumparator) {
        isCumparator = cumparator;
    }
}