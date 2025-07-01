package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Afișarea profilului utilizatorului
    @GetMapping
    public String showProfile(Model model, Principal principal) {
        String username = principal.getName();

        // Preia utilizatorul și detaliile asociate
        User user = userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> cumparatorDetails = userRepository.getCumparatorDetails(user.getId());
        Map<String, Object> vanzatorDetails = userRepository.getVanzatorDetails(user.getId());

        // Verifică rolurile utilizatorului
        boolean isCumparator = userRepository.isCumparator(user.getId());
        boolean isVanzator = userRepository.isVanzator(user.getId());

        // Adaugă datele în model
        model.addAttribute("user", user);
        model.addAttribute("cumparatorDetails", cumparatorDetails);
        model.addAttribute("vanzatorDetails", vanzatorDetails);
        model.addAttribute("isCumparator", isCumparator);
        model.addAttribute("isVanzator", isVanzator);

        return "profile"; // Returnează pagina de profil
    }

    // Afișarea formularului de editare
    @GetMapping("/edit")
    public String editProfileForm(Model model, Principal principal) {
        String username = principal.getName();

        // Preia utilizatorul și detaliile asociate
        User user = userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> cumparatorDetails = userRepository.getCumparatorDetails(user.getId());

        // Adaugă datele utilizatorului în model
        model.addAttribute("user", user);
        model.addAttribute("cumparatorDetails", cumparatorDetails);

        return "edit-profile"; // Returnează pagina pentru editare
    }

    // Procesarea editării
    @PostMapping("/edit")
    public String updateProfile(@RequestParam Map<String, String> formData, Principal principal) {
        String username = principal.getName();

        // Preia utilizatorul curent
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Actualizează detaliile utilizatorului
        userRepository.updateUserDetails(
                user.getId(),
                formData.get("email"),
                formData.get("password")
        );

        // Actualizează detaliile cumpărătorului, dacă există
        if (userRepository.isCumparator(user.getId())) {
            userRepository.updateCumparatorDetails(
                    user.getId(),
                    formData.get("nume"),
                    formData.get("prenume"),
                    formData.get("localitate"),
                    formData.get("strada"),
                    Integer.parseInt(formData.get("nr")),
                    formData.get("sex"),
                    formData.get("dataNasterii"),
                    Integer.parseInt(formData.get("nrOferte"))
            );
        }

        return "redirect:/profile";
    }

    // Devino cumpărător
    @PostMapping("/devino-cumparator")
    public String devinoCumparator(Principal principal) {
        String username = principal.getName();

        // Găsește utilizatorul în baza de date
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Inserează utilizatorul în tabelul Cumparatori
        userRepository.addCumparator(user.getId());

        return "redirect:/profile";
    }

    // Devino vânzător
    @PostMapping("/devino-vanzator")
    public String devinoVanzator(Principal principal) {
        String username = principal.getName();

        // Găsește utilizatorul în baza de date
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Inserează utilizatorul în tabelul Vanzatori
        userRepository.addVanzator(user.getId());

        return "redirect:/profile";
    }
}
