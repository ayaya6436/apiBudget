package apiBudget.apiBudget.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetsPodio {
    private String date_debut;
    private BigDecimal montant;
    private Long id_users;
    private Long id_categories;
}
