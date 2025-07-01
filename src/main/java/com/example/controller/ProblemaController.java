package com.example.controller;

import com.example.model.Problema;
import com.example.model.User;
import com.example.service.ProblemaService;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/ajutor")
public class ProblemaController {

    private final ProblemaService problemaService;
    private final UserRepository userRepository;

    public ProblemaController(ProblemaService problemaService, UserRepository userRepository) {
        this.problemaService = problemaService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getProblemeByUtilizator(Model model, Principal principal) {
        // Obține username-ul utilizatorului conectat
        String username = principal.getName();

        // Obține utilizatorID pe baza username-ului
        int utilizatorID = userRepository.findIdByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Preia problemele utilizatorului
        model.addAttribute("probleme", problemaService.getProblemeByUtilizator(utilizatorID));

        return "ajutor"; // Returnează pagina cu lista de probleme
    }

    @PostMapping("/add")
    public String addProblema(@ModelAttribute Problema problema, Principal principal) {
        // Obține username-ul utilizatorului conectat
        String username = principal.getName();

        User utilizator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        problema.setUtilizatorID(utilizator.getId());

    // Adaugă problema
        problemaService.addProblema(problema);
        return "redirect:/ajutor"; // Redirecționează la lista de probleme
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Problema problema = problemaService.getProblemaById(id);
        model.addAttribute("problema", problema);
        return "editare-problema"; // Returnează pagina pentru editare
    }
    @PostMapping("/edit/{id}")
    public String updateProblema(@PathVariable("id") int problemaID, @ModelAttribute Problema problema) {
        problemaService.updateProblema(problemaID, problema);
        return "redirect:/ajutor";
    }

    @GetMapping("/delete/{id}")
    public String deleteProblema(@PathVariable("id") int problemaID) {
        problemaService.deleteProblema(problemaID);
        return "redirect:/ajutor";
    }
}
