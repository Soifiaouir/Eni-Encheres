package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Controller
@SessionAttributes({"userSession"})
public class ProfilController {

    private final UtilisateurService service;

    public ProfilController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping("/profil")
    public String viewProfil(@RequestParam(name = "user") long id, Model model, Principal principal) {

        return "view-profil";
    }

    @GetMapping("/credit")
    public String buyCredit() {
        return "credit";
    }
}
