package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface ArticleVenduService {

    List<ArticleVendu> getLstArticleVendus();

    ArticleVendu getArticleVenduByNoArticle(long noArticle);

    String etatEnchere(ArticleVendu articleVendu);

    void createArticleVendu(ArticleVendu articleVendu);

    void updateArticleVendu(ArticleVendu articleVendu);

    void deleteArticleVendu(ArticleVendu articleVendu);

    String getNameArticleVendu(long noArticle);

}
