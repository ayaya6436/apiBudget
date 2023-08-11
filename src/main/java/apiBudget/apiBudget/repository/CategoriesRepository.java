package apiBudget.apiBudget.repository;


import apiBudget.apiBudget.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {

    Categories findCategoriesById(Long id);
    Categories getByNom(String nom);

}
