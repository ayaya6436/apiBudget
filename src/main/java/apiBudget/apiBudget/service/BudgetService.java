package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Budgets;

import java.util.List;

public interface BudgetService {
    String creer(Budgets budgets);
    Budgets lire(Long id);
    String modifier(Long id,Budgets budgets);
    String supprimer(Long id);
}