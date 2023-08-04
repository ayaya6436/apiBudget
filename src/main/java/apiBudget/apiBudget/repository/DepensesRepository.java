package apiBudget.apiBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apiBudget.apiBudget.model.Depenses;

public interface DepensesRepository extends JpaRepository<Depenses,Long> {
    
}
