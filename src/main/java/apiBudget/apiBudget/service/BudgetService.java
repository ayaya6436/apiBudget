package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Budgets;

import java.time.LocalDate;
import java.util.List;

public interface BudgetService {
    String creer(Budgets budgets);
    Budgets lire(Long id);
    String modifier(Long id,Budgets budgets);
    String supprimer(Long id);
    Boolean Notactive(LocalDate date, Long id1,Long id2);
    double depense_total(Long id);
    Boolean Incurrentbudget(LocalDate date,Long id_user,Long id_categorie);
}
