package com.example.controller;

import com.example.model.MasaLicitatie;
import com.example.model.Telefon;
import com.example.model.Vanzator;
import com.example.repository.MasaLicitatieRepository;
import com.example.repository.StatisticsRepository;
import com.example.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final StatisticsService statisticsService;
    private final MasaLicitatieRepository masaLicitatieRepository;
    private final StatisticsRepository statisticsRepository;

    public HomeController(StatisticsService statisticsService, MasaLicitatieRepository masaLicitatieRepository, StatisticsRepository statisticsRepository) {
        this.statisticsService = statisticsService;
        this.masaLicitatieRepository = masaLicitatieRepository;
        this.statisticsRepository = statisticsRepository;
    }
//metoda pentru afisarea paginii home
    @GetMapping("/home")
    public String home(
            @RequestParam(value = "maxValue", required = false, defaultValue = "1000") double maxValue,
            Model model) {

        List<Telefon> telefoane = statisticsService.getAllPhones();
        List<Map<String, Object>> categorii = statisticsService.getPhoneCategories();
        List<Vanzator> vanzatoriTop = statisticsService.getTopVanzatori();
        List<Map<String, Object>> ultimeleTelefoaneVandute = statisticsService.getRecentlySoldPhones();
        List<Map<String, Object>> topVanzatoriInTopCategorii = statisticsRepository.getVanzatoriCuBranduriPopulare();

        List<Map<String, Object>> vanzatori = statisticsService.findVanzatoriWithAvgTransactionBelow(maxValue);

        model.addAttribute("telefoane", telefoane);
        model.addAttribute("categorii", categorii);
        model.addAttribute("vanzatoriTop", vanzatoriTop);
        model.addAttribute("ultimeleTelefoaneVandute", ultimeleTelefoaneVandute);
        model.addAttribute("topVanzatoriInTopCategorii", topVanzatoriInTopCategorii);
        model.addAttribute("vanzatoriPretMediu", vanzatori);
        model.addAttribute("maxValue", maxValue);

        return "home";
    }

    @GetMapping("/licitatii")
    public String getLicitatiiDisponibile(Model model) {
        List<MasaLicitatie> masaLicitatii = masaLicitatieRepository.findActiveLicitatiiWithPhones();

        // Formatter pentru data și timp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        // Formatarea timpului pentru fiecare licitație
        for (MasaLicitatie licitatie : masaLicitatii) {
            licitatie.setFormattedStartTimp(licitatie.getStartTimp().format(formatter));
            licitatie.setFormattedStopTimp(licitatie.getStopTimp().format(formatter));
        }

        model.addAttribute("licitatii", masaLicitatii);
        return "licitatii";
    }
}
