package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Alertes;
import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.EmailDetails;
import apiBudget.apiBudget.repository.BudgetsRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class BudgetServiceImpl implements BudgetService{
    @Autowired
    private BudgetsRepository budgetsRepository;
    @Autowired
    private AlerteService alerteService;
    @Autowired
    private EmailServiceImpl emailServiceIplm;

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

    @Override
    public void checkBudgetStatus(Budgets budgets, BigDecimal montantRestant) {
        double budgetAmount = budgets.getMontant();
        BigDecimal budgetAmountBigDecimal = BigDecimal.valueOf(budgetAmount);

        BigDecimal montantRestant1 = budgetAmountBigDecimal.subtract(montantRestant);
        // Vérification de réduction de budget
        BigDecimal fiftyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.5"));
        BigDecimal seventyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.3"));
        BigDecimal ninetyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.1"));
        if (montantRestant1.compareTo(fiftyPercent) <= 0) {
            createBudgetAlert(budgets, montantRestant, 50);
        }
        if (montantRestant1.compareTo(seventyPercent) <= 0) {
            createBudgetAlert(budgets, montantRestant, 30);
        }
        if (montantRestant1.compareTo(ninetyPercent) <= 0) {
            createBudgetAlert(budgets, montantRestant, 10);
        }

        // Vérification de montant restant égal à 0
        if (montantRestant1.compareTo(BigDecimal.ZERO) == 0) {
            createBudgetAlert(budgets, montantRestant, 0);
        }



        //findu ckeckB

    }
    private void createBudgetAlert(Budgets budget, BigDecimal montantRestant, int percentage) {
        String message = "Votre budget a diminué de " + percentage + "% et votre montant restant est de " + montantRestant;
        Alertes alertes = new Alertes();
        alertes.setDate_alertes(LocalDate.now());
        alertes.setDescription(message);
        alertes.setBudgets(budget);
        alerteService.creer(alertes);
        //Alert

        EmailDetails details = new EmailDetails(budget.getUsers().getEmail(), message, "Details du Budget");
        emailServiceIplm.sendSimpleMail(details);

    }
}
