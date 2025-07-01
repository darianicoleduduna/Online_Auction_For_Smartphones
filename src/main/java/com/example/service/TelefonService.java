package com.example.service;

import com.example.model.Telefon;
import com.example.repository.TelefonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TelefonService {

    private final TelefonRepository telefonRepository;

    public TelefonService(TelefonRepository telefonRepository) {
        this.telefonRepository = telefonRepository;
    }

    public List<Telefon> getAllTelefoane() {
        return telefonRepository.findAll();
    }

    public Telefon getTelefonById(int id) {
        return telefonRepository.findById(id);
    }

    public void saveTelefon(Telefon telefon) {
        telefonRepository.save(telefon);
    }

    public void updateTelefon(Telefon telefon) {
        telefonRepository.update(telefon);
    }

    public void deleteTelefonById(Long id) {
        telefonRepository.deleteById(id);
    }
    public List<Telefon> findPhonesByTopSellers() {
        return telefonRepository.findPhonesByTopSellers();
    }
    public List<Telefon> getPhonesByCategory(String brand) {
        return telefonRepository.findPhonesByCategory(brand);
    }
    public List<String> getAllBrands() {
        return telefonRepository.getAllBrands();
    }


}
