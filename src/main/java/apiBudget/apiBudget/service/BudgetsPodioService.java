package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.*;
import apiBudget.apiBudget.repository.BudgetsRepository;
import apiBudget.apiBudget.repository.CategoriesRepository;
import apiBudget.apiBudget.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@Service
public class BudgetsPodioService {
    private UsersRepository usersRepository;
    private CategoriesRepository categoriesRepository;
    private BudgetsRepository budgetsRepository;

    @Autowired
    private AlerteService alerteService;
    @Autowired
    private EmailServiceImpl emailServiceIplm;


    public BudgetsPodioService(UsersRepository usersRepository, CategoriesRepository categoriesRepository, BudgetsRepository budgetsRepository) {
        this.usersRepository = usersRepository;
        this.categoriesRepository = categoriesRepository;
        this.budgetsRepository = budgetsRepository;
    }
    public String creer(BudgetsPodio budgetsPodio){
        //on verifie la date
        LocalDate dateToday = LocalDate.now();
        //precisement on parse le string en un format LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date ;
        if (budgetsPodio.getDate_debut()==null){
            return "La date doit etre saisi";
        }
        try {
            date = LocalDate.parse(budgetsPodio.getDate_debut(),formatter);
        }catch (DateTimeParseException e){
            return "Veuillez saisir une date correcte";
        }
        //Ici on verifie qu'on est dans un delai de un mois dans le passe
        //Pour cela on convertit LocalDate en LocalDateTime ensuite pour pouvoir faire des operation arithimetique sur la date
        LocalDateTime datepassetime = dateToday.atStartOfDay().minusMonths(1);
        LocalDate datepasse = datepassetime.toLocalDate();

        //LocalDateTime datefuturtime = dateToday.atStartOfDay().plusMonths(1);
        //LocalDate datefutur = datefuturtime.toLocalDate();

        if (datepasse.isBefore(date) && dateToday.isAfter(date) || date.equals(dateToday)){

            Budgets budgets = new Budgets();
            //determinons la date de fin
            LocalDateTime datefintime = date.atStartOfDay().plusMonths(1);
            LocalDate datefin = datefintime.toLocalDate();

            LocalDateTime datelimit = dateToday.atStartOfDay();

            Long joursRestant = ChronoUnit.DAYS.between(datelimit, datefintime);
            String warning = "";
            if (joursRestant <= 10){
                warning = "Il ne vous reste que "+joursRestant+" jours avant la fin du budget";
            }

            //on a defini les date debut et de fin
            budgets.setDate_debut(date);
            budgets.setDate_fin(datefin);
            Users users = usersRepository.findUsersById(budgetsPodio.getId_users());
            if (users!=null){
                if (budgetsRepository.findBudgetsByCategories_Id(budgetsPodio.getId_categories())!=null){
                    return "Un budgets a ete defini pour cette categories";
                }
                budgets.setUsers(users);
                if (budgetsPodio.getMontant()>0){
                    budgets.setMontant(budgetsPodio.getMontant());
                    Categories categories = categoriesRepository.findCategoriesById(budgetsPodio.getId_categories());
                    if (categories!=null){
                        budgets.setCategories(categories);
                        budgetsRepository.save(budgets);
                        //Alert
                        String msg = "Votre Budget a été defini avec " +budgets.getMontant()+ " FCFA \n"+warning;
                        EmailDetails details = new EmailDetails(budgets.getUsers().getEmail(), msg, "Details du Budget");
                        emailServiceIplm.sendSimpleMail(details);


                        Alertes alertes = new Alertes();
                        alertes.setDate_alertes(dateToday);
                        alertes.setDescription(msg);
                        alertes.setBudgets(budgets);
                        alerteService.creer(alertes);
                        //Fin d'alert
                        return  "Budget defini avec succes\n"+warning;
                    }else {
                        return "Desole cette categorie n'existe pas";
                    }
                }else {
                    return "Desole un Tel budget n'exite pas ";
                }

            }else {
                return "Cet user n'existe pas";
            }

        }else {
            return "Ce n'est pas un mois courant";
        }
    }
    //Alert auto
    public void checkBudgetStatus(Budgets budget, BigDecimal montantRestant) {
        double budgetAmount = budget.getMontant();
        BigDecimal budgetAmountBigDecimal = BigDecimal.valueOf(budgetAmount);

        BigDecimal montantRestant1 = budgetAmountBigDecimal.subtract(montantRestant);
        // Vérification de réduction de budget
        BigDecimal fiftyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.5"));
        BigDecimal seventyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.3"));
        BigDecimal ninetyPercent = budgetAmountBigDecimal.multiply(new BigDecimal("0.1"));
        if (montantRestant1.compareTo(fiftyPercent) <= 0) {
            createBudgetAlert(budget, montantRestant, 50);
        }
        if (montantRestant1.compareTo(seventyPercent) <= 0) {
            createBudgetAlert(budget, montantRestant, 30);
        }
        if (montantRestant1.compareTo(ninetyPercent) <= 0) {
            createBudgetAlert(budget, montantRestant, 10);
        }

        // Vérification de montant restant égal à 0
        if (montantRestant1.compareTo(BigDecimal.ZERO) == 0) {
            createBudgetAlert(budget, montantRestant, 0);
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






