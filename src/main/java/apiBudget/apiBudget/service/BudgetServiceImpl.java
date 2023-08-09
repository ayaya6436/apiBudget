package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.Categories;
import apiBudget.apiBudget.model.Depenses;
import apiBudget.apiBudget.repository.BudgetsRepository;
import apiBudget.apiBudget.repository.CategoriesRepository;
import apiBudget.apiBudget.repository.DepensesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService{
    private BudgetsRepository budgetsRepository;
    private DepensesRepository depensesRepository;

    private CategoriesRepository categoriesRepository;

    public BudgetServiceImpl(BudgetsRepository budgetsRepository,CategoriesRepository categoriesRepository, DepensesRepository depensesRepository) {
        this.budgetsRepository = budgetsRepository;
        this.depensesRepository = depensesRepository;
        this.categoriesRepository = categoriesRepository;
    }

    //private CategoriesRepository categoriesRepository;
    @Override
    public String creer(Budgets budgets) {
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
                    test.setDebut(budgets.getDebut());
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

    @Override
    //on verifie qu'il nya pas de bubget active par rapport a une date du user
    public  Boolean Notactive(LocalDate date, Long id1,Long id2) {
        List<Budgets> list = budgetsRepository.findAllByFinAfterAndUsers_IdAndCategories_Id(date,id1,id2);
        if (list.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public double depense_total(Long id) {
        List<Depenses> list = depensesRepository.findAllByBudgets_Id(id);
        double total = 0;
        for (Depenses depense : list ) {
            total = total + depense.getMontant();
        }
        return total;
    }


}
