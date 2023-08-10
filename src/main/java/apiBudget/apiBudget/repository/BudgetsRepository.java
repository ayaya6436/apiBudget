package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface BudgetsRepository extends JpaRepository<Budgets,Long> {
    Budgets findBudgetsByCategories_Id(Long id);
    List<Budgets> findAllByFinAfterAndUsers_IdAndCategories_Id(LocalDate date,Long id1,Long id2);

    List<Budgets> findAllByUsers_IdOrderByFinDesc(Long id);
    List<Budgets> findAllByUsers_IdAndFinAfterOrFinEquals(Long id,LocalDate date1,LocalDate date2);

    Budgets findFirstTop1ByUsers_IdOrderByFinDesc(Long id);


    List<Budgets> findAllByFinAndUsers_Id(LocalDate date,Long id);

    List<Categories> findDistinctByUsers_Id(Long id);

    List<Budgets> findDistinctFinByUsers_IdAAndCAndCategories_IdOrderByFinDesc(Long id,Long id2);



}
