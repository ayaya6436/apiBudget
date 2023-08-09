package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BudgetsRepository extends JpaRepository<Budgets,Long> {
    Budgets findBudgetsByCategories_Id(Long id);
    List<Budgets> findAllByFinAfterAndUsers_IdAndCategories_Id(LocalDate date,Long id1,Long id2);

    List<Budgets> findAllByUsers_IdOrderByFinDesc(Long id);
    List<Budgets> findAllByUsers_IdAndFinAfterOrFinEquals(Long id,LocalDate date1,LocalDate date2);

    Budgets findFirstTop1ByUsers_IdOrderByFinDesc(Long id);

    Budgets findFirstTop2ByUsers_IdOrderByFinDesc(Long id);

    List<Budgets> findAllByFinAndUsers_Id(LocalDate date,Long id);



}
