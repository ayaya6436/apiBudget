package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.*;
import apiBudget.apiBudget.repository.BudgetsRepository;
import apiBudget.apiBudget.repository.CategoriesRepository;
import apiBudget.apiBudget.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private BudgetService budgetService;


    @Autowired
    public BudgetsPodioService(UsersRepository usersRepository,BudgetService budgetService, CategoriesRepository categoriesRepository, BudgetsRepository budgetsRepository) {
        this.usersRepository = usersRepository;
        this.categoriesRepository = categoriesRepository;
        this.budgetsRepository = budgetsRepository;
        this.budgetService = budgetService;
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
        //Verifions que la date est en accord avec le budget precedent s'il existe

        if (budgetService.Notactive(date,budgetsPodio.getId_users(),budgetsPodio.getId_categories())){
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
                /*if (budgetsRepository.findBudgetsByCategories_Id(budgetsPodio.getId_categories())!=null){
                    return "Un budgets a ete defini pour cette categories";
                }*/
                budgets.setUsers(users);
                if (budgetsPodio.getMontant()>0){
                    budgets.setMontant(budgetsPodio.getMontant());
                    Categories categories = categoriesRepository.findCategoriesById(budgetsPodio.getId_categories());
                    if (categories!=null){
                        budgets.setCategories(categories);
                        budgetsRepository.save(budgets);
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
        }else {
            return "Cette date de debut rentre dans un budget deja defini";
        }
    }

    public Object lire(Long id,int choix){
        List<BudgetsAffichage> affichageList = new ArrayList<>();
        List<Budgets> list = new ArrayList<>();
        if (choix==1){
            //Mois courant
            list = budgetsRepository.findAllByUsers_IdAndFinAfterOrFinEquals(id,LocalDate.now(),LocalDate.now());
        } else if (choix == 2) {
            list = budgetsRepository.findAllByUsers_Id(id);
        }else {
            list = budgetsRepository.findAllByUsers_Id(id);
        }
        for (Budgets budget: list) {
            BudgetsAffichage budgetsAffichage = new BudgetsAffichage();

            budgetsAffichage.setId_budget(budget.getId());

            budgetsAffichage.setId_categorie(budget.getCategories().getId());

            budgetsAffichage.setDate_fin(budget.getFin());

            budgetsAffichage.setMontant(budget.getMontant());

            //Calcule du jours restant
            int rest = (int) ChronoUnit.DAYS.between(LocalDate.now().atTime(23,59,59) , budget.getFin().atTime(23,59,59));
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
            budgetsAffichage.setMontant_restant(budgetsAffichage.getMontant()-budgetsAffichage.getMontant_consommee());
            affichageList.add(budgetsAffichage);
        }
        if (affichageList.isEmpty()&&choix==1){
            return "Pas de budget pour le mois courant";
        } else if (affichageList.isEmpty()&&choix==2) {
            return "Pas de budget pour le mois passe";
        }
        return affichageList;
    }
}

