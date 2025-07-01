package com.example.service;

import com.example.model.Telefon;
import com.example.model.Vanzator;
import com.example.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    // Afișează toate telefoanele și statusul lor
    public List<Telefon> getAllPhones() {
        return statisticsRepository.findAllPhones();
    }

    // Categorii disponibile
    public List<Map<String, Object>> getPhoneCategories() {
        return statisticsRepository.findPhoneCategories();
    }

    // Top vânzători
    public List<Vanzator> getTopVanzatori() {
        return statisticsRepository.findTopVanzatori();
    }

    // Ultimele telefoane vândute
    public List<Map<String, Object>> getRecentlySoldPhones() {
        return statisticsRepository.findRecentlySoldPhones();
    }
    public List<Map<String, Object>> findVanzatoriWithAvgTransactionBelow(double maxValue) {
        return statisticsRepository.findVanzatoriWithAvgTransactionBelow(maxValue);
    }
}
