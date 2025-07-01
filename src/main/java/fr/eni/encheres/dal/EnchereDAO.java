package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;

public interface EnchereDAO {
    Enchere readByNoEnchere(Long noEnchere);
    void createEnchere(Enchere enchere);
    void deleteEnchere(long noEnchere);
    void updateEnchere(Enchere enchere);


}
