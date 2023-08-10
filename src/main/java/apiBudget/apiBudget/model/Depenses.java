package apiBudget.apiBudget.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "depenses")
@Setter
@Getter
@NoArgsConstructor

public class Depenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String titre;

    private BigDecimal montant;

    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date_depenses;

    @Column(length = 250)
    private String note;

    // une depense ne peut etre lie qu'a un et un seul user
    @ManyToOne
    @JoinColumn(name = "id_users", nullable = true)
    private Users users;

    // une depense ne peut etre lie qu'a une et une seule categorie

    @ManyToOne
    @JoinColumn(name = "id_types", nullable = true)

    private apiBudget.apiBudget.model.Types types;
    
    // une depense est deduite d'une et une seule budget
    @ManyToOne
    @JoinColumn(name = "id_budgets", nullable = true)
    private Budgets budgets;

}
