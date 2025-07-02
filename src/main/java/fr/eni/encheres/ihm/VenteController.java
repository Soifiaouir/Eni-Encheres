package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@Controller
public class VenteController {

    ArticleVenduService articleVenduService;

    public VenteController(ArticleVenduService articleVenduService) {
        this.articleVenduService = articleVenduService;
    }

    @GetMapping("/articles")
    public String displayArticles(Model model) {
        List<ArticleVendu> list = articleVenduService.getLstArticleVendus();
        model.addAttribute("articlesLst", list);
        return "articles";
    }

    @GetMapping("/fiche_article")
    public String MettreArticleEnVente(Model model) {
        model.addAttribute("ArticleVendu", new ArticleVendu());

        return "details_article";
    }

    @PostMapping ("/vendre")
    public String venteArticlePost(@Valid @ModelAttribute("articleVendu") ArticleVendu articleVendu,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "details_article";
        }

        articleVenduService.createArticleVendu(articleVendu);

        return "redirect:/vente_article";

    }

    @GetMapping("/voir_vente")
    public String displayVente(@RequestParam(name="noArticle") long noArticle, Model model) {
        ArticleVendu articleVendu = articleVenduService.getArticleVenduByNoArticle(noArticle);
        model.addAttribute("ArticleVendu", articleVendu);

        return "vente_article";

    }

    }








