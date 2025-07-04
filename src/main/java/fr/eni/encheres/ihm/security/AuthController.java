package fr.eni.encheres.ihm.security;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.UtilisateurDTO;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtilisateurService service,  PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());
        return "signin";
    }

    @PostMapping("/signin")
    public String newUtilisateur(@Valid @ModelAttribute("utilisateurDTO") UtilisateurDTO utilisateurDTO,
                                 BindingResult bindingResult) {

        int countPseudo = service.checkPseudo(utilisateurDTO.getPseudo());
        int countEmail = service.checkEmail(utilisateurDTO.getEmail());

        if (!utilisateurDTO.getMotDePasse().equals(utilisateurDTO.getMotDePasseConfirm())) {
            bindingResult.rejectValue("motDePasseConfirm", "validation.pwd-confirm");
        }
        if (countPseudo > 0) {
            bindingResult.rejectValue("pseudo", "validation.pseudo.unique");
        }
        if (countEmail > 0) {
            bindingResult.rejectValue("email", "validation.email.unique");
        }
        if (bindingResult.hasErrors()) {
            return "signin";
        }
        try {
            utilisateurDTO.setMotDePasse(passwordEncoder.encode(utilisateurDTO.getMotDePasse()));

            Utilisateur utilisateur = getUtilisateur(utilisateurDTO);
            service.createUtilisateur(utilisateur);
            return "redirect:/?signin=true";
        } catch (BusinessException e) {
            e.getClefsExternalisations().forEach(key -> {
                ObjectError error = new ObjectError("globalError", key);
                bindingResult.addError(error);
            });
        }
        return "signin";
    }

    private Utilisateur getUtilisateur(UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setPseudo(utilisateurDTO.getPseudo());
        utilisateur.setNom(utilisateurDTO.getNom());
        utilisateur.setPrenom(utilisateurDTO.getPrenom());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setTelephone(utilisateurDTO.getTelephone());
        utilisateur.setRue(utilisateurDTO.getRue());
        utilisateur.setCodePostal(utilisateurDTO.getCodePostal());
        utilisateur.setVille(utilisateurDTO.getVille());
        utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
        utilisateur.setCredit(utilisateurDTO.getCredit());
        utilisateur.setAdministrateur(utilisateurDTO.isAdministrateur());

        return utilisateur;
    }
}
