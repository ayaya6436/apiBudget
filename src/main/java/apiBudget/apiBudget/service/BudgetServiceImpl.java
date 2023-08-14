package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Alertes;
import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.Depenses;
import apiBudget.apiBudget.model.EmailDetails;
import apiBudget.apiBudget.repository.BudgetsRepository;
import apiBudget.apiBudget.repository.DepensesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetsRepository budgetsRepository;
    private DepensesRepository depensesRepository;

    @Autowired
    private AlerteService alerteService;

    @Autowired
    private EmailServiceImpl emailServiceIplm;

    @Override
    public String creer(Budgets budgets) {
        budgets.setMontantRestant(new BigDecimal(budgets.getMontant().doubleValue()));
        budgetsRepository.save(budgets);
        return "Votre budget a été défini avec succès";
    }

    @Override
    public Budgets lire(Long id) {
        return budgetsRepository.findById(id).orElse(null);
    }

    @Override
    public String modifier(Long id, Budgets budgets) {
        return budgetsRepository.findById(id)
            .map(test -> {
                test.setMontant(budgets.getMontant());
                test.setDebut(budgets.getDebut());
                budgetsRepository.save(test);
                return "Votre budget a été modifié avec succès";
            }).orElseThrow(() -> new RuntimeException("Désolé, nous n'avons pas pu trouver ce budget"));
    }

    @Override
    public String supprimer(Long id) {
        budgetsRepository.deleteById(id);
        return "Vous avez supprimé ce budget";
    }

    @Override
    public void checkBudgetStatus(Budgets budgets, BigDecimal montantRestant) {
        //Mes Alertes
        BigDecimal budgetAmount = budgets.getMontant();
        BigDecimal budgetAmountBigDecimal = budgetAmount;

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
    }

    private void createBudgetAlert(Budgets budget, BigDecimal montantRestant, int percentage) {
        String message = "Votre budget a diminué de " + percentage + "% et votre montant restant est de " + montantRestant;
        Alertes alertes = new Alertes();
        alertes.setDate_alertes(LocalDate.now());
        alertes.setDescription(message);
        alertes.setBudgets(budget);
        alerteService.creer(alertes);

        EmailDetails details = new EmailDetails(budget.getUsers().getEmail(), message, "Détails du Budget");
        emailServiceIplm.sendSimpleMail(details);
    }
     @Override
    //on verifie qu'il nya pas de bubget active par rapport a une date du user
    public  Boolean Notactive(LocalDate date, Long id1,Long id2) {
        List<Budgets> list = budgetsRepository.findAllByFinAfterOrFinEqualsAndUsers_IdAndCategories_Id(date,date,id1,id2);
        if (list.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public BigDecimal depense_total(Long id) {
        List<Depenses> list = depensesRepository.findAllByBudgets_Id(id);
        BigDecimal total = BigDecimal.ZERO; // Initialiser avec zéro
        
        for (Depenses depense : list) {
            total = total.add(depense.getMontant()); // Utiliser la méthode add() de BigDecimal
        }
        
        return total;
    }
    

    @Override
    public Boolean Incurrentbudget(LocalDate date,Long id_user, Long id_categorie){
        LocalDate datetoday = LocalDate.now();
        List<Budgets> budgets = budgetsRepository.findDistinctFinByUsers_IdAndCategories_IdOrderByFinDesc(id_user,id_categorie);
        if (!budgets.isEmpty()){
            if ((datetoday.isAfter(budgets.get(0).getDebut())||datetoday.isEqual(budgets.get(0).getDebut())) && (datetoday.isBefore(budgets.get(0).getFin()) || datetoday.isEqual(budgets.get(0).getFin()))){
                //ca veut dire qu'il y a un budget courant pour cette categorie
                if (date.isAfter(budgets.get(0).getDebut())||date.isEqual(budgets.get(0).getDebut()) && date.isBefore(budgets.get(0).getFin()) || date.isEqual(budgets.get(0).getFin())){
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    
}
