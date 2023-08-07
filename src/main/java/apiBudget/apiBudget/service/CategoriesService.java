package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Categories;

import java.util.List;

public interface CategoriesService {
    String creer(Categories categories);
    List<Categories> Lire();
    Categories getCategorieById(Long id);
    //Utilisateur supprimer(Long id, Utilisateur utilisateur);


    Categories modifier(Long id, Categories categories);
    String supprimer(Long id);
}
