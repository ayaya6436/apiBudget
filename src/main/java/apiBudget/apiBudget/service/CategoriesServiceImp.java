package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Categories;
import apiBudget.apiBudget.repository.CategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImp implements CategoriesService{
    private CategoriesRepository categoriesRepository;
    public CategoriesServiceImp(CategoriesRepository categoriesRepository){
        this.categoriesRepository=categoriesRepository;
    }

    @Override
    public String creer(Categories categories) {
        categoriesRepository.save(categories);
        return "Categories creer avec succes";
    }

    @Override
    public List<Categories> lire() {
        return null;
    }

    @Override
    public Categories lire(Long id_categories) {
        return categoriesRepository.findById(id_categories).orElse(null);
    }

    @Override
    public String supprimer(Long id_categories) {
        categoriesRepository.deleteById(id_categories);
        return "votre categorie a été supprimer avec Succès";
    }
}
