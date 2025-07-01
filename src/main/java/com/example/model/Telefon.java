package com.example.model;

public class Telefon {
    private int telefonId;
    private String model;
    private String statusLicitat;
    private double pret;
    private String descriere;
    private int anFabricatie;
    private String culoare;
    private String memorie;
    private String statusStoc;

    // Getters și Setters
    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    public double getPret() {
        return pret;
    }
    public int getAnFabricatie() {
        return anFabricatie;
    }

    public void setAnFabricatie(int anFabricatie) {
        this.anFabricatie = anFabricatie;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public String getMemorie() {
        return memorie;
    }

    public void setMemorie(String memorie) {
        this.memorie = memorie;
    }

    public String getStatusStoc() {
        return statusStoc;
    }

    public void setStatusStoc(String statusStoc) {
        this.statusStoc = statusStoc;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }
    public int getTelefonId() {
        return telefonId;
    }

    public void setTelefonId(int telefonId) {
        this.telefonId = telefonId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatusLicitat() {
        return statusLicitat;
    }

    public void setStatusLicitat(String statusLicitat) {
        this.statusLicitat = statusLicitat;
    }
}
