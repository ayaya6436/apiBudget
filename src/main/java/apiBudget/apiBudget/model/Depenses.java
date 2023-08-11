package apiBudget.apiBudget.model;

import java.math.BigDecimal;
import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonFormat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

   
   @Column(length=50, nullable = false, unique = true)
   @NotBlank(message = "Le titre ne doit pas être vide !")
   @NotNull(message = "Le titre ne doit pas être null")
   @Size(min = 2 ,message = "Le titre est trop court", max = 225)
    private String titre;

     
    @DecimalMin(value = "0", inclusive = false, message = "Le montant doit être supérieur à 0")
    @Digits(integer = 10, fraction = 2, message = "Le montant doit être un nombre valide avec au maximum 2 décimales")
    private BigDecimal montant;

    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date_depenses;

   @Column(length=50, nullable = false)
   @NotBlank(message = "La note ne doit pas être vide !")
   @NotNull(message = "La note ne doit pas être null")
   @Size(min = 2 ,message = "La note  ne doit pas etre trop court", max = 225)
   
    private String note;

    // une depense ne peut etre lie qu'a un et un seul user
    @ManyToOne
    @JoinColumn(name = "id_users", nullable = false)
    private Users users;

    // une depense ne peut etre lie qu'a une et une seule categorie

    @ManyToOne
    @JoinColumn(name = "id_types", nullable = false)

    private apiBudget.apiBudget.model.Types types;
    
    // une depense est deduite d'une et une seule budget
    @ManyToOne

    @JoinColumn(name = "id_budgets", nullable = false)

    private Budgets budgets;

}
