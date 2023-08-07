package apiBudget.apiBudget.repository;


import apiBudget.apiBudget.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {
    Categories findCategoriesById(Long id);
}
