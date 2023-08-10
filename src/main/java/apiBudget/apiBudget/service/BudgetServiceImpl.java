package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.repository.BudgetsRepository;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService{
    private BudgetsRepository budgetsRepository;

    public BudgetServiceImpl(BudgetsRepository budgetsRepository) {
        
        this.budgetsRepository = budgetsRepository;
    }

    //private CategoriesRepository categoriesRepository;
    @Override
    public String creer(Budgets budgets) {
        budgets.setMontantRestant(BigDecimal.valueOf(budgets.getMontant())); // Initialisation du montant restant
        budgetsRepository.save(budgets);
        return "Votre budget a ete defini avec succes";
    }

    @Override
    public Budgets lire(Long id) {
       return budgetsRepository.findById(id).orElse(null);
    }

    @Override
    public String modifier(Long id,Budgets budgets) {
        return budgetsRepository.findById(id)
                .map(test->{
                    test.setMontant(budgets.getMontant());
                    test.setDate_debut(budgets.getDate_debut());
                    //test.setCategories(categoriesRepository.findById(budgets.getCategories().getId_categories()));
                    budgetsRepository.save(budgets);
                    return "Votre budget a ete modifie avec succes";
                }).orElseThrow(()->new RuntimeException("Desole nous n'avons pas pu trouver cet budget") );
    }

    @Override
    public String supprimer(Long id) {
        budgetsRepository.deleteById(id);
        return "Vous avez supprimer cet budgets";
    }
}
