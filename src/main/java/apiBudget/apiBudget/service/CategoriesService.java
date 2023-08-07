package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Categories;

import java.util.List;

public interface CategoriesService {
    String creer(Categories categories);
    List<Categories> lire();
    Categories lire(Long id_categories);
    String supprimer(Long id_categories);
}
