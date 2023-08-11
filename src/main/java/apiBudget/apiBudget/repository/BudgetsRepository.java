package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface BudgetsRepository extends JpaRepository<Budgets,Long> {
    Budgets findBudgetsByCategories_Id(Long id);

}
