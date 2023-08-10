package apiBudget.apiBudget.model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BudgetsAffichage {
    private Long id_budget;
    private Long id_categorie;
    private double montant;
    private LocalDate date_fin;
    private String status;
    private int jours_restant;
    private double montant_consommee;
    private double montant_restant;

}
