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

@RequestMapping("/articles")
@Controller
public class VenteController {

    ArticleVenduService articleVenduService;

    public VenteController(ArticleVenduService articleVenduService) {
        this.articleVenduService = articleVenduService;
    }

    @GetMapping("/list_articles")
    public String PagesListesEncheres(Model model) {
        List<ArticleVendu> list = articleVenduService.getLstArticleVendus();
        model.addAttribute("articlesLst", list);
        return "view_article_list";
    }

    @GetMapping("/create_article")
    public String PageVendreUnArticle(Model model) {
        model.addAttribute("ArticleVendu", new ArticleVendu());

        return "create_article";
    }

    @PostMapping ("/create_article")
    public String PageVendreUnArticlePost(@Valid @ModelAttribute("articleVendu") ArticleVendu articleVendu,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create_article";
        }

        articleVenduService.createArticleVendu(articleVendu);

        return "view_article";

    }

    @GetMapping("/view_article")
    public String PageEnchereNonCommencee(@RequestParam(name="noArticle") long noArticle, Model model) {
        ArticleVendu articleVendu = articleVenduService.getArticleVenduByNoArticle(noArticle);
        model.addAttribute("ArticleVendu", articleVendu);

        return "view_article";

    }

    }








