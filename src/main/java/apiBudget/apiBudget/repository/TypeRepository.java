package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.Types;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Types,Long> {
    Types getTypeByLibelle(String libelle);
}
