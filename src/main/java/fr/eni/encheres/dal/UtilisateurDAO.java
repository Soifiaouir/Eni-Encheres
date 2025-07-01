package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {

    // CRUD
    public void createUtilisateur(Utilisateur utilisateur);
    public void updateUtilisateur(Utilisateur utilisateur);
    public Utilisateur readUtilisateurById(long id);
    public List<Utilisateur> readAllUtilisateurs();
    public void deleteUtilisateur(long id);

    // MDP OUBLIE
    public int checkUtilisateurByLogin(String pseudo, String email);
    public String findPseudoByEmail(String email);

}
