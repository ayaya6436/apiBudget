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

        try {
            //  Block of code to try
            Categories cat1 = categoriesRepository.getByNom(categories.getNom());
            if(cat1 == null && categories.getNom() != null && categories.getNom() != "" && categories.getNom() != " "){
                categoriesRepository.save(categories);
                return "Catégorie creer avec succès !";

            } else if (cat1 != null) {
                return "Cette catégorie existe déjà !";

            } else if (categories.getNom() == null || categories.getNom() == "" || categories.getNom() == " ") {

                return "Le nom né doit pas être vide ou null";
            }

        }
        catch(RuntimeException e) {
            //  Block of code to handle errors
            return "message: "+e;
        }
        return "Cette catégorie existe déjà !";
    }

    @Override
    public List<Categories> Lire() {
        List<Categories> categoriesList = categoriesRepository.findAll();

        if (categoriesList.isEmpty()) {
            System.out.println("La liste est vide.");
        }

        return categoriesList;


    }

    @Override
    public Categories getCategorieById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(()-> new RuntimeException("Catégorie non trouvé !"));
    }

    @Override
    public String modifier(Long id, Categories categories) {
        Categories cat1 = categoriesRepository.getByNom(categories.getNom());
        Categories cat2 = categoriesRepository.findById(id).orElseThrow(()-> new RuntimeException("Catégorie non trouvé !"));
        if(cat1 != null && cat2 != null){
             categoriesRepository.findById(id).map(cat ->{

                cat.setNom(categories.getNom());
                cat.setBudgets(categories.getBudgets());

                categoriesRepository.save(cat);
                return " Catégorie modifier avec succès ! ";
            }).orElseThrow(()-> new RuntimeException("Catégorie non trouvé !"));
        }else if (cat1 == null) {
             return "Cette catégorie existe déjà !";

        } else if (cat2 == null) {
            return "Cette catégorie n'existe pas !";

        }
        return null;


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
