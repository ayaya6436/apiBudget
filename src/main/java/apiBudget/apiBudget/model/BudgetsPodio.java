package apiBudget.apiBudget.model;

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
