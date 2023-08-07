package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Categories;
import apiBudget.apiBudget.repository.CategoriesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class CategoriesServiceImp implements CategoriesService{
    @Autowired
   private final CategoriesRepository categoriesRepository;

    @Override
    public String creer(Categories categories) {
        Categories cat1 = categoriesRepository.getByNom(categories.getNom());
        if(cat1 == null){
            categoriesRepository.save(categories);
            return "Catégorie creer avec succès !";

        } else if (cat1 != null) {
            return "Cette catégorie existe déjà !";

        }
        return null;

    }

    @Override
    public List<Categories> Lire() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories getCategorieById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(()-> new RuntimeException("Catégorie non trouvé !"));
    }

    @Override
    public Categories modifier(Long id, Categories categories) {
        return categoriesRepository.findById(id).map(cat ->{

            cat.setNom(categories.getNom());
            cat.setBudgets(categories.getBudgets());

            return categoriesRepository.save(cat);
        }).orElseThrow(()-> new RuntimeException("Catégorie non trouvé !"));
    }

    @Override
    public String supprimer(Long id) {
        Categories categorie = categoriesRepository.findById(id).orElseThrow(()-> new RuntimeException("Catégprie non trouvé !"));

        if (categorie != null ){
            categoriesRepository.deleteById(categorie.getId());
            return "Catégorie supprimer avec succès !";
        }
        if (categorie == null){
            return "Utilisateur non trouvé !";
        }
        return null;
    }
}
