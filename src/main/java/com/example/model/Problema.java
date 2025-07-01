package com.example.model;

public class Problema {
    private int problemaID;
    private int adminID;
    private int utilizatorID;
    private String descriere;
    private String status;
    private String adminNume; // Numele adminului
    private String adminPrenume; // Prenumele adminului

    // Getters și Setters
    public int getProblemaID() {
        return problemaID;
    }

    public void setProblemaID(int problemaID) {
        this.problemaID = problemaID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getUtilizatorID() {
        return utilizatorID;
    }

    public void setUtilizatorID(int utilizatorID) {
        this.utilizatorID = utilizatorID;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminNume() {
        return adminNume;
    }

    public void setAdminNume(String adminNume) {
        this.adminNume = adminNume;
    }

    public String getAdminPrenume() {
        return adminPrenume;
    }

    public void setAdminPrenume(String adminPrenume) {
        this.adminPrenume = adminPrenume;
    }
}
