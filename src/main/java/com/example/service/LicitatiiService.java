package com.example.service;

import com.example.repository.LicitatiiRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LicitatiiService {

    private final LicitatiiRepository licitatiiRepository;

    public LicitatiiService(LicitatiiRepository licitatiiRepository) {
        this.licitatiiRepository = licitatiiRepository;
    }

    public Map<String, Object> getLicitatieDetails(int masaID) {
        return licitatiiRepository.findLicitatieDetails(masaID);
    }

    public List<Map<String, Object>> getOferteForMasa(int masaID) {
        return licitatiiRepository.findOferteForMasa(masaID);
    }

    public void addOferta(int masaID, double suma, String username) {
        licitatiiRepository.saveOferta(masaID, suma, username);
    }
}
