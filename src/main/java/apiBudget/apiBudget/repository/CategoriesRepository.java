package apiBudget.apiBudget.repository;
<<<<<<< HEAD
=======


>>>>>>> 060cf37cd14e753c607e530cf5aa34b1b8d39559
import apiBudget.apiBudget.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {

    Categories findCategoriesById(Long id);
    Categories getByNom(String nom);

}
