package apiBudget.apiBudget.model;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.Categories;
import apiBudget.apiBudget.model.Users;
import apiBudget.apiBudget.repository.BudgetsRepository;
import apiBudget.apiBudget.repository.CategoriesRepository;
import apiBudget.apiBudget.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetsPodio {
    private String date_debut;
    private double montant;
    private Long id_users;
    private Long id_categories;
}
