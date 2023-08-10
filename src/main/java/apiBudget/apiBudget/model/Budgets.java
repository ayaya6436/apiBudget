package apiBudget.apiBudget.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budgets")
@Setter
@Getter
@NoArgsConstructor
public class Budgets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date_debut;
    private LocalDate date_fin;

    private double montant;
    private BigDecimal montantRestant;

//un budget ne peut etre lie qu'a un et un seul user

    @ManyToOne()
    @JoinColumn(name = "id_users",nullable = false)
    @JsonBackReference
    private Users users;

//une categorie ne peut etre lie qu'a un et un seul budget
    @ManyToOne()
    @JoinColumn(name = "id_categories",nullable = false)
    @JsonIgnore
    private Categories categories;

      //un budget peut effectuer 0 ou plusieurs depenses
      
   @OneToMany(mappedBy = "budgets", cascade = CascadeType.ALL)
   @JsonIgnore

   private List<Depenses> depenses;

   //un budget  peut avoir 0 ou plusieurs alertes
   @OneToMany(mappedBy = "budgets", cascade = CascadeType.ALL)
   private List<Alertes> alertes;

}
