package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.AlertConditionBoolean;
import apiBudget.apiBudget.model.Alertes;
import apiBudget.apiBudget.repository.AlertCondiRepositor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class AlertConditService implements ALERTCOND {
    @Autowired
    private AlertCondiRepositor alertCondiRepositor;

    @Override
    public String creerB1(AlertConditionBoolean pourcent1) {
       pourcent1.setAlertSent1(true);
        alertCondiRepositor.save(pourcent1);
        return  "dzdd";
    }

    @Override
    public String creerB2(AlertConditionBoolean pourcent2) {
        pourcent2.setAlertSent1(true);
        alertCondiRepositor.save(pourcent2);
        return "p2";
    }

    @Override
    public String creerB3(AlertConditionBoolean pourcent3) {
        pourcent3.setAlertSent1(true);
        alertCondiRepositor.save(pourcent3);
        return "p3";
    }
}
