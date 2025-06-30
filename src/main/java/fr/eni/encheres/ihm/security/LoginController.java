package fr.eni.encheres.ihm.security;

import fr.eni.encheres.bll.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    private UtilisateurService service;

    public LoginController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
