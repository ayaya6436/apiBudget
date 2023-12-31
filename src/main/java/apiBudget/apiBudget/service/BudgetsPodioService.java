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
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetsPodioService {
    private UsersRepository usersRepository;
    private CategoriesRepository categoriesRepository;
    private BudgetsRepository budgetsRepository;
    private BudgetService budgetService ;

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
        if (Valid.dates(budgetsPodio.getDate_debut())){
            date = LocalDate.parse(budgetsPodio.getDate_debut(),formatter);
        }else {
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
            budgets.setDebut(date);
            budgets.setFin(datefin);
            Users users = usersRepository.findUsersById(budgetsPodio.getId_users());
            if (users!=null){
                if (budgetsRepository.findBudgetsByCategories_Id(budgetsPodio.getId_categories())!=null){
                    return "Un budgets a ete defini pour cette categories";
                }
                budgets.setUsers(users);
                if (budgetsPodio.getMontant().intValue()>0){
                    budgets.setMontant(budgetsPodio.getMontant());
                    Categories categories = categoriesRepository.findCategoriesById(budgetsPodio.getId_categories());
                    if (categories!=null){
                        budgets.setCategories(categories);
                        budgetsRepository.save(budgets);
                        //Alert
                        String msg = "Votre Budget a été defini avec un montant de " +budgets.getMontant()+ " FCFA \n"+warning;
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

    public Object lire(Long id,int choix){
        List<BudgetsAffichage> affichageList = new ArrayList<>();
        List<Budgets> list = new ArrayList<>();
        if (choix==1){
            //Mois courant
            list = budgetsRepository.findAllByUsers_IdAndFinAfterOrFinEquals(id,LocalDate.now(),LocalDate.now());
        } else if (choix == 2) {
            //Essayons d'obtenir une liste des budget passe
            //Obtenons d'abord la liste des categorie disponible
            List<Long> categoriesList = budgetsRepository.findDistinctByUsers_Id(id);

            //Maintenant obtenons le dernier budget defini pour chaque categorie

            for (Long categorie: categoriesList ) {
                List<Budgets> results  = budgetsRepository.findDistinctFinByUsers_IdAndCategories_IdOrderByFinDesc(id,categorie);
                if (results.size()==1){
                    //Ca veut dire qu'un budget defini pour cette categorie donc verifions que ce n'est pas un mois courant
                    if (!budgetService.Incurrentbudget(results.get(0).getFin(),id,categorie)){
                        list.add(results.get(0));
                    }
                } else if (results.size()>=2) {
                    //ca veut dire qu'on au moins deux date
                    if (!budgetService.Incurrentbudget(results.get(0).getFin(),id,categorie)){
                        list.add(results.get(0));
                    }else {
                        list.add(results.get(1));
                    }
                }
            }


        }else {
            list = budgetsRepository.findAllByUsers_IdOrderByFinDesc(id);
        }
        for (Budgets budget: list) {
            BudgetsAffichage budgetsAffichage = new BudgetsAffichage();

            budgetsAffichage.setId_budget(budget.getId());

            budgetsAffichage.setId_categorie(budget.getCategories().getId());

            budgetsAffichage.setDate_fin(budget.getFin());

            budgetsAffichage.setMontant(budget.getMontant());



            //Calcule du jours restant
            int rest = (int) ChronoUnit.DAYS.between(LocalDate.now().atStartOfDay() , budget.getFin().atStartOfDay());
            //Verifions le status
            if (rest < 0){
               budgetsAffichage.setStatus("Termine");
               budgetsAffichage.setJours_restant(0);
            }else {
                budgetsAffichage.setStatus("En cours");
                budgetsAffichage.setJours_restant(rest);
            }

            //Calcule du montant consomme
            budgetsAffichage.setMontant_consommee(budgetService.depense_total(budget.getId()));
            budgetsAffichage.setMontant_restant(budgetsAffichage.getMontant().subtract(budgetsAffichage.getMontant_consommee()));
            affichageList.add(budgetsAffichage);
        }
        if (affichageList.isEmpty()&&choix==1){
            return "Pas de budget pour le mois courant";
        } else if (affichageList.isEmpty()&&choix==2) {
            return "Pas de budget pour le mois passe";
        }
        return affichageList;
    }
    //Alert auto

    //transfer de budget pour un nouveau budget si la date du budget s'expire

}






