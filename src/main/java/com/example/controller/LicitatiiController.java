package com.example.controller;

import com.example.service.LicitatiiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


    @Controller
    @RequestMapping("/licitatii")
    public class LicitatiiController {

        private final LicitatiiService licitatiiService;

        public LicitatiiController(LicitatiiService licitatiiService) {
            this.licitatiiService = licitatiiService;
        }
        //metoda pentru afisarea detaliilor unei mese de licitatie
        @GetMapping("/detalii/{masaID}")
        public String getLicitatieDetails(@PathVariable("masaID") int masaID, Model model) {
            // Preia detalii despre licitație
            Map<String, Object> licitatieDetails = licitatiiService.getLicitatieDetails(masaID);
            List<Map<String, Object>> oferte = licitatiiService.getOferteForMasa(masaID);

            // Adaugă datele în model
            model.addAttribute("licitatieDetails", licitatieDetails);
            model.addAttribute("oferte", oferte);
            model.addAttribute("masaID", masaID);

            return "licitatii-detalii";
        }

        //metoda pentru adaugare oferta
        @PostMapping("/detalii/{masaID}/addOferta")
        public String addOferta(@PathVariable("masaID") int masaID, @RequestParam("suma") double suma, Principal principal) {
            // Preia utilizatorul curent din sesiune
            System.out.println("masaID: " + masaID);
            String username = principal.getName();
            licitatiiService.addOferta(masaID, suma, username);

            return "redirect:/licitatii/detalii/" + masaID; // Redirecționează la pagina detaliilor
        }
    }


