package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.BudgetsPodio;
import apiBudget.apiBudget.model.Categories;
import apiBudget.apiBudget.model.Users;
import apiBudget.apiBudget.repository.BudgetsRepository;
import apiBudget.apiBudget.repository.CategoriesRepository;
import apiBudget.apiBudget.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
@Service
public class BudgetsPodioService {
    private UsersRepository usersRepository;
    private CategoriesRepository categoriesRepository;
    private BudgetsRepository budgetsRepository;


    @Autowired
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
        //Ici on verifie qu'on est dans un delai de un mois dans le passe ou dans le futur
        //Pour cela on convertit LocalDate en LocalDateTime ensuite pour pouvoir faire des operation arithimetique sur la date
        LocalDateTime datepassetime = dateToday.atStartOfDay().minusMonths(1);
        LocalDate datepasse = datepassetime.toLocalDate();

        LocalDateTime datefuturtime = dateToday.atStartOfDay().plusMonths(1);
        LocalDate datefutur = datefuturtime.toLocalDate();

        if (datepasse.isBefore(date) && datefutur.isAfter(date) ){
            Budgets budgets = new Budgets();
            //determinons la date de fin
            LocalDateTime datefintime = date.atStartOfDay().plusMonths(1);
            LocalDate datefin = datefintime.toLocalDate();
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
                        return "Budget defini avec succes";
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
}

