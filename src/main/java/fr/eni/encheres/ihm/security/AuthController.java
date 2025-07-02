package fr.eni.encheres.ihm.security;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UtilisateurService service;

    public AuthController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "signin";
    }

    @PostMapping("/signin")
    public String newUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/signin";
        }
        int countPseudo = service.checkPseudo(utilisateur.getPseudo());
        int countEmail = service.checkEmail(utilisateur.getEmail());

        if (countPseudo == 0 && countEmail == 0) {
            try {
                service.createUtilisateur(utilisateur);
                return "redirect:/login?signin=true";
            } catch (BusinessException e) {
                e.getClefsExternalisations().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
            }
        } else {
            if (countPseudo > 0) {
                model.addAttribute("errorPseudo", utilisateur.getPseudo());
            }
            if (countEmail > 0) {
                model.addAttribute("errorEmail", utilisateur.getEmail());
            }
            model.addAttribute("userReturn", utilisateur);

        }
        return "/signin";
    }
}
