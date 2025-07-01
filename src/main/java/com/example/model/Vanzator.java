package com.example.model;

import java.util.List;

public class Vanzator {
    private int vanzatorId;
    private String nume;
    private String prenume;
    private float rating;
    private List<String> telefoane;

    // Getters și Setters
    public int getVanzatorId() {
        return vanzatorId;
    }

    public void setVanzatorId(int vanzatorId) {
        this.vanzatorId = vanzatorId;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<String> getTelefoane() {
        return telefoane;
    }

    public void setTelefoane(List<String> telefoane) {
        this.telefoane = telefoane;
    }
}
