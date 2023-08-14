package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.Budgets;
import org.springframework.data.jpa.repository.JpaRepository;

import apiBudget.apiBudget.model.Depenses;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepensesRepository extends JpaRepository<Depenses,Long> {
    List<Depenses> findAllByBudgets_Id(Long id);
    @Query("SELECT e FROM Depenses e WHERE e.budgets = ?1 ")
    List<Depenses> findByBudget(Budgets budgets);

    
}
