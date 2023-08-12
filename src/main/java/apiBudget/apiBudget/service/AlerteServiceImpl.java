package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.*;
import apiBudget.apiBudget.repository.AlertesRepository;
import lombok.Data;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class AlerteServiceImpl implements AlerteService {
    private AlertesRepository alertesRepository;
    private boolean alertSent1 = false;
    private boolean alertSent2 = false;
    private boolean alertSent3 = false;

    public boolean isAlertSent1() {
        return alertSent1;
    }

    public void setAlertSent1(boolean alertSent1) {
        this.alertSent1 = alertSent1;
    }

    public boolean isAlertSent2() {
        return alertSent2;
    }

    public void setAlertSent2(boolean alertSent2) {
        this.alertSent2 = alertSent2;
    }

    public boolean isAlertSent3() {
        return alertSent3;
    }

    public void setAlertSent3(boolean alertSent3) {
        this.alertSent3 = alertSent3;
    }
    public AlerteServiceImpl(AlertesRepository alertesRepository) {
        this.alertesRepository = alertesRepository;
    }

    @Override
    public List<Alertes> Lire() {
        return alertesRepository.findAll();
    }

    @Override
    public String creer(Alertes alertes) {
         alertesRepository.save(alertes);
         return "Alert Envoyer avec succès !";
    }

    @Override
    public String supprimer(Long id) {
        Alertes alertes = alertesRepository.findById(id).orElseThrow(()-> new RuntimeException("Alert non trouvé !"));

        if (alertes != null ){
            alertesRepository.deleteById(alertes.getId());
            return "Alert supprimer avec succès !";
        }
        if (alertes == null){
            return "Utilisateur non trouvé !";
        }
        return null;
    }
    /* /++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void checkBudgetStatus(Budget budget, BigDecimal amountSpent) {
        //Mes Alertes
        BigDecimal budgetAmount = budget.getMontant();
        BigDecimal budgetAmountBigDecimal = budgetAmount;
        boolean alertSent = false;

        //BigDecimal montantRestant1 = budgetAmountBigDecimal.subtract(montantRestant);
        // Vérification de réduction de budget
        BigDecimal fiftyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.5"));
        BigDecimal seventyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.35"));
        BigDecimal ninetyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.1"));
        //==================================================///
        if (montantRestant.compareTo(BigDecimal.ZERO) == 0) {

            // Vérification de montant restant égal à 0
            createBudgetAlert(budget, montantRestant, 0);




        }
        else if (montantRestant.compareTo(ninetyPercent) <= 0 && !alertSent){
            // block of code to be executed if the condition1 is false and condition2 is true
            createBudgetAlert(budget, montantRestant, 10);
            alertSent = true;


        }
        else if (montantRestant.compareTo(seventyPercent) <= 0 && !alertSent) {
            // block of code to be executed if the condition1 is false and condition2 is true
            createBudgetAlert(budget, montantRestant, 35);
            alertSent = true;

        }  else if (montantRestant.compareTo(fiftyPercent) <= 0 && !alertSent) {
            createBudgetAlert(budget, montantRestant, 50);
            alertSent = true;
        }


        //Mes Alertes
    }

    private void createBudgetAlert(Budgets budget, BigDecimal montantRestant, int percentage) {
        String message = "Votre budget a diminué de " + percentage + "% et votre montant restant est de " + montantRestant;
        Alertes alertes = new Alertes();
        alertes.setDate_alertes(LocalDate.now());
        alertes.setDescription(message);
        alertes.setBudgets(budget);
        alerteService.creer(alertes);
        //Alert
        EmailDetails details = new EmailDetails();
        //EmailDetails details = new EmailDetails(budget.getUsers().getEmail(), message, "Details du Budget");
        details.setEmail(budget.getUsers().getEmail());
        //System.out.println("==============================="+details.getEmail()+"=============================");
        details.setMessageBody(message);
        details.setSujet("Details du Budget");
        emailServiceIplm.sendSimpleMail(details);


    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */

}
