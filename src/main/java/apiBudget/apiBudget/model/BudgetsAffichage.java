package apiBudget.apiBudget.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetsAffichage {
    private Long id_budget;
    private Long id_categorie;
    private BigDecimal montant;
    private LocalDate date_fin;
    private String status;
    private int jours_restant;
    private BigDecimal montant_consommee;
    private BigDecimal montant_restant;

}
