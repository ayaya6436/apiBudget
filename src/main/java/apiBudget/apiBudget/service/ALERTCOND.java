package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.AlertConditionBoolean;
import apiBudget.apiBudget.model.Alertes;

public interface ALERTCOND {
    String creerB1(AlertConditionBoolean pourcent1);
    String creerB2(AlertConditionBoolean pourcent2);
    String creerB3(AlertConditionBoolean pourcent3);
}
