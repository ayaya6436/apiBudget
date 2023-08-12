package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Budgets;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BudgetService {
    String creer(Budgets budgets);
    Budgets lire(Long id);
    String modifier(Long id,Budgets budgets);
    String supprimer(Long id);
    void checkBudgetStatus(Budgets budgets, BigDecimal montantRestant);
    Boolean Notactive(LocalDate date, Long id1,Long id2);
    BigDecimal depense_total(Long id);
    Boolean Incurrentbudget(LocalDate date,Long id_user,Long id_categorie);
}
