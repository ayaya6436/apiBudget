package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.AlertConditionBoolean;
import apiBudget.apiBudget.model.Alertes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertCondiRepositor extends JpaRepository<AlertConditionBoolean, Long> {

}
