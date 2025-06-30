package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessCode;
import fr.eni.encheres.exception.BusinessException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDAO dao;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.dao = utilisateurDAO;
    }

    @Override
    public void createUtilisateur(Utilisateur utilisateur) {
        dao.createUtilisateur(utilisateur);
    }

    @Override
    public void updateUtilisateur(Utilisateur utilisateur) {
        dao.updateUtilisateur(utilisateur);
    }

    @Override
    public void deleteUtilisateur(long id) {
        dao.deleteUtilisateur(id);
    }

    @Override
    public Utilisateur findUtilisateurById(long id) {
       try {
           return dao.readUtilisateurById(id);
       }  catch (DataAccessException e) {
           throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
       }
    }

    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        try {
            return dao.readAllUtilisateurs();
        }  catch (DataAccessException e) {
            throw new BusinessException(BusinessCode.DB_UTILISATEUR_INCONNU);
        }
    }
}
