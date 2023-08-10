package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface BudgetsRepository extends JpaRepository<Budgets,Long> {
    Budgets findBudgetsByCategories_Id(Long id);
    List<Budgets> findAllByFinAfterAndUsers_IdAndCategories_Id(LocalDate date,Long id1,Long id2);

    List<Budgets> findAllByUsers_IdOrderByFinDesc(Long id);
    List<Budgets> findAllByUsers_IdAndFinAfterOrFinEquals(Long id,LocalDate date1,LocalDate date2);

    @Query(value = "SELECT DISTINCT c.* " +
            "FROM categories c " +
            "JOIN budgets b ON c.id = b.id_categories " +
            "WHERE b.id_users = :id", nativeQuery = true)
    List<Long> findDistinctByUsers_Id(@Param("id") Long id);

    List<Budgets> findDistinctFinByUsers_IdAndCategories_IdOrderByFinDesc(Long id,Long id2);



}
