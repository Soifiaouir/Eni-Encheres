package fr.eni.encheres.bll;

import fr.eni.encheres.bo.*;
import fr.eni.encheres.dal.ArticleVenduDAO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {

    private ArticleVenduDAO articleVenduDAO;

    // private UtilisateurDAO utilisateurDAO;

    // private EnchereDAO enchereDAO;

    // private CategorieDAO categorieDAO;

    // private RetraitDAO  retraitDAO;

    public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
        this.articleVenduDAO = articleVenduDAO;
    }

    /** Method used to get the list of all the articles
     *
     * @return
     */

    @Override
    public List<ArticleVendu> getLstArticleVendus() {
        List<ArticleVendu> lstArticlesVendus = articleVenduDAO.getAllArticleVendu();

      /*  if (lstArticlesVendus != null) {
            lstArticlesVendus.forEach(articleVendu -> {
                setUtilisateurEncheresRetraitCategorie1article(articleVendu);
            });
        } */

        return lstArticlesVendus;
    }

    /** Method used to get one article by his id (noArticle)
     *
     * @param noArticle
     * @return the article
     */

    @Override
    public ArticleVendu getArticleVenduByNoArticle(long noArticle) {
        ArticleVendu articleVendu = articleVenduDAO.getArticleVendu(noArticle);

     /*   if (articleVendu != null) {
            setUtilisateurEncheresRetraitCategorie1article(articleVendu);

            // TODO : faire un lien entre EnchereDAO et l'ArticleVendu

            // TODO faire un lien potentiel avec d'autres DAO

        } */

        return articleVendu;
    }

    /** Private method to make the association between an article and their other settings
     * (Utilisateur, Enchere, Categorie, Retrait)
     *
     * @param articleVendu
     */

  /**  private void setUtilisateurEncheresRetraitCategorie1article (ArticleVendu articleVendu) {
        Utilisateur utilisateur = utilisateurDAO.read(articleVendu.getUtilisateur().getNoUtilisateur());
        articleVendu.setUtilisateur(utilisateur);
        List <Enchere> lstEncheres = enchereDAO.read(articleVendu.getLstEncheres());
        articleVendu.setLstEncheres(lstEncheres);
        Categorie categorie = categorieDAO.read(articleVendu.getCategorie().getNoCategorie());
        articleVendu.setCategorie(categorie);
        Retrait retrait = retraitDAO.read(articleVendu.getLieuRetrait());
        articleVendu.setLieuRetrait(retrait);

    } **/

    /** Method used to know what is the state of the auction (begun, ended, not already began)
     *
     * @param articleVendu
     * @return a String with the actual state of the auction.
     */

    @Override
    public String etatEnchere(ArticleVendu articleVendu){
        LocalDate dateDebut = articleVendu.getDateDebutEncheres();
        LocalDate dateFin = articleVendu.getDateFinEncheres();
        LocalDate today = LocalDate.now();
        if (dateDebut != null && dateFin != null){
            if (today.isBefore(dateDebut)) {
                return "l'enchère n'a pas commencée";
            }
            if (today.isAfter(dateFin)) {
                return "l'enchère est finie";
            }
            else return "l'enchère est en cours";
        }
        return "erreur de la mise en enchère";
    }


    @Override
    public void createArticleVendu(ArticleVendu articleVendu) {
        articleVenduDAO.createArticle(articleVendu);
    }

    @Override
    public void updateArticleVendu(ArticleVendu articleVendu) {
        articleVenduDAO.updateArticle(articleVendu);
    }

    @Override
    public void deleteArticleVendu(ArticleVendu articleVendu) {
        long noArticle = articleVendu.getNoArticle();
        articleVenduDAO.removeArticle(noArticle);
    }

    @Override
    public String getNameArticleVendu(long noArticle) {
        return articleVenduDAO.findNomArticle(noArticle);
    }







}
