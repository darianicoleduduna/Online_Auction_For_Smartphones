package com.example.controller;

import com.example.model.Telefon;
import com.example.service.TelefonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/telefoane")
public class TelefonController {

    private final TelefonService telefonService;

    public TelefonController(TelefonService telefonService) {
        this.telefonService = telefonService;
    }

    // Afișează toate telefoanele sau filtrează după brand
    @GetMapping
    public String showPhones(
            @RequestParam(value = "brand", required = false) String brand,
            Model model) {
        List<Telefon> telefoane;
        boolean showAll = (brand == null || brand.isEmpty());

        if (!showAll) {
            // Filtrează telefoanele după brand
            telefoane = telefonService.getPhonesByCategory(brand);
            model.addAttribute("selectedBrand", brand);
        } else {
            // Afișează toate telefoanele
            telefoane = telefonService.getAllTelefoane();
            model.addAttribute("selectedBrand", null);
        }

        // Adaugă lista de branduri pentru dropdown
        List<String> brands = telefonService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("telefoane", telefoane);
        model.addAttribute("showAll", showAll); // Controlează starea butonului

        return "telefoane";
    }

    @PostMapping("/add")
    public String addTelefon(@ModelAttribute Telefon telefon) {
        telefonService.saveTelefon(telefon);
        return "redirect:/telefoane";
    }

    @GetMapping("/edit/{id}")
    public String editTelefonForm(@PathVariable("id") int id, Model model) {
        Telefon telefon = telefonService.getTelefonById(id);
        model.addAttribute("telefon", telefon);
        return "edit-telefon"; // Creează o pagină separată pentru editare
    }

    @PostMapping("/edit/{id}")
    public String updateTelefon(@PathVariable("id") int id, @ModelAttribute Telefon telefon) {
        telefon.setTelefonId(id); // Setăm ID-ul telefonului, deoarece este preluat din URL
        telefonService.updateTelefon(telefon);
        return "redirect:/telefoane";
    }

    @GetMapping("/delete/{id}")
    public String deleteTelefon(@PathVariable("id") Long id) {
        telefonService.deleteTelefonById(id);
        return "redirect:/telefoane";
    }

    // Afișează telefoanele vândute de cei mai buni vânzători
    @GetMapping("/filter/top-sellers")
    public String filterPhonesByTopSellers(@RequestParam(value = "brand", required = false) String brand, Model model) {
        List<Telefon> telefoane;

        if (brand != null && !brand.isEmpty()) {
            // Filtrează telefoanele după brand
            telefoane = telefonService.getPhonesByCategory(brand);
            model.addAttribute("selectedBrand", brand);
        } else {
            // Afișează telefoanele vândute de top vânzători
            telefoane = telefonService.findPhonesByTopSellers();
            model.addAttribute("selectedBrand", null);
        }

        // Adaugă brandurile în model
        List<String> brands = telefonService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("telefoane", telefoane);
        model.addAttribute("showAll", false);

        return "telefoane";
    }
    @GetMapping("/all")
    public String getAllPhones(@RequestParam(value = "brand", required = false) String brand, Model model) {
        List<Telefon> telefoane;

        if (brand != null && !brand.isEmpty()) {
            // Filtrează telefoanele după brand
            telefoane = telefonService.getPhonesByCategory(brand);
            model.addAttribute("selectedBrand", brand);
        } else {
            // Afișează toate telefoanele
            telefoane = telefonService.getAllTelefoane();
            model.addAttribute("selectedBrand", null);
        }

        // Adaugă brandurile în model
        List<String> brands = telefonService.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("telefoane", telefoane);
        model.addAttribute("showAll", true);

        return "telefoane";
    }

}

