package apiBudget.apiBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apiBudget.apiBudget.model.Depenses;

import java.util.List;

public interface DepensesRepository extends JpaRepository<Depenses,Long> {
    List<Depenses> findAllByBudgets_Id(Long id);
    
}
