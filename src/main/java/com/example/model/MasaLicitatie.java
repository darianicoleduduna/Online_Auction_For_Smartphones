package com.example.model;

import java.time.LocalDateTime;

public class MasaLicitatie {

    private Integer masaID;
    private LocalDateTime startTimp;
    private LocalDateTime stopTimp;
    private Integer minOferta;
    private Integer maxOferta;
    private Integer castigator;
    private Integer telefonID;
    private Integer adminID;
    private String formattedStartTimp;
    private String formattedStopTimp;
    private String pozaUrl;
    private String modelTelefon;

    public String getPozaUrl() {
        return pozaUrl;
    }

    public void setPozaUrl(String pozaUrl) {
        this.pozaUrl = pozaUrl;
    }

    // Constructori, Getters și Setters
    public MasaLicitatie() {}

    public Integer getMasaID() {
        return masaID;
    }

    public void setMasaID(Integer masaID) {
        this.masaID = masaID;
    }

    public LocalDateTime getStartTimp() {
        return startTimp;
    }

    public void setStartTimp(LocalDateTime startTimp) {
        this.startTimp = startTimp;
    }

    public LocalDateTime getStopTimp() {
        return stopTimp;
    }

    public void setStopTimp(LocalDateTime stopTimp) {
        this.stopTimp = stopTimp;
    }

    public Integer getMinOferta() {
        return minOferta;
    }

    public void setMinOferta(Integer minOferta) {
        this.minOferta = minOferta;
    }

    public Integer getMaxOferta() {
        return maxOferta;
    }

    public void setMaxOferta(Integer maxOferta) {
        this.maxOferta = maxOferta;
    }

    public Integer getCastigator() {
        return castigator;
    }

    public void setCastigator(Integer castigator) {
        this.castigator = castigator;
    }

    public Integer getTelefonID() {
        return telefonID;
    }

    public void setTelefonID(Integer telefonID) {
        this.telefonID = telefonID;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public String getFormattedStartTimp() {
        return formattedStartTimp;
    }

    public void setFormattedStartTimp(String formattedStartTimp) {
        this.formattedStartTimp = formattedStartTimp;
    }

    public String getFormattedStopTimp() {
        return formattedStopTimp;
    }

    public void setFormattedStopTimp(String formattedStopTimp) {
        this.formattedStopTimp = formattedStopTimp;
    }
    public String getModelTelefon() {
        return modelTelefon;
    }

    public void setModelTelefon(String modelTelefon) {
        this.modelTelefon = modelTelefon;
    }
}
