package fr.eni.encheres.dal.impl;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.EnchereDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

    private final String SELECT_ID = "SELECT NO_ENCHERE FROM ENCHERES WHERE NO_ENCHERE = :noEnchere";
    private final String CREATE_ENCHERE ="INSERT INTO ENCHERES (DATE_ENCHERE, MONTANT_ENCHERE,NO_ARTICLE,NO_UTILISATEUR)" +
            " VALUE (date_enchere,:montant_enchere, :no_article, :no_utilisateur)";
    private final String FIND_ALL_ENCHERES ="SELECT NO_ENCHERE, DATE_ENCHERE, MONTANT_ENCHERE, NO_ARTICLE, NO_UTILISATEUR FROM ENCHERES";

    private final String DELETE_ENCHERE ="DELETE FROM ENCHERES WHERE NO_ENCHERE = :noEnchere";
    private final String UPDATE_ENCHERE = "UPDATE ENCHERES SET DATE_ENCHERE = :dateeEnchere, " +
            "MONTANT-ENCHERE = :montant_enchere, NO_ENCHERE= :no_enchere," +
            " NO_UTILISATEUR = :no_utilisateur where NO_ENCHERE = :no_enchere";


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public EnchereDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Enchere readByNoEnchere(long noEnchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_enchere", noEnchere);

        return jdbcTemplate.queryForObject(SELECT_ID,params, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public List<Enchere> findListEncheres(long noArticleVendu) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getJdbcTemplate())
                .withProcedureName("FindListEncheres")
                .returningResultSet("encheres",
                        new BeanPropertyRowMapper<Enchere>(Enchere.class));

        SqlParameterSource in = new MapSqlParameterSource().addValue("no_article", noArticleVendu);
        Map<String, Object> result = jdbcCall.execute(in);

        if (result.get("encheres" )!=null) {
            List<Enchere> encheres = (List<Enchere>)result.get("encheres");
            return encheres;
        }

        return null;
    }

    @Override
    public void createEnchere(Enchere enchere) {
        KeyHolder key = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_enchere", enchere.getNoEnchere());
        params.addValue("date_enchere", enchere.getDateEnchere());
        params.addValue("montant_enchere", enchere.getMontantEnchere());
        params.addValue("no_article", enchere.getArticleConcerne().getNoArticle());
        params.addValue("no_utilisateur", enchere.getEncherisseur().getNoUtilisateur());

        jdbcTemplate.update(CREATE_ENCHERE, params, key);

        if ( key != null && key.getKey() != null ) {
            enchere.setNoEnchere(key.getKey().longValue());
        }

    }

    @Override
    public void deleteEnchere(long noEnchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_enchere", noEnchere);

        jdbcTemplate.update(DELETE_ENCHERE, params);
    }

    @Override
    public void updateEnchere(Enchere enchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("date_enchere", enchere.getDateEnchere());
        params.addValue("montant_enchere", enchere.getMontantEnchere());
        params.addValue("montant_enchere", enchere.getMontantEnchere());
        params.addValue("no_article", enchere.getArticleConcerne().getNoArticle());
        params.addValue("no_utilisateur", enchere.getEncherisseur().getNoUtilisateur());

        jdbcTemplate.update(UPDATE_ENCHERE, params);

    }
}
