package apiBudget.apiBudget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import apiBudget.apiBudget.model.Depenses;

public interface DepensesRepository extends JpaRepository<Depenses,Long> {

    List<Depenses> findAllByBudgets_Id(Long id);
    
}
