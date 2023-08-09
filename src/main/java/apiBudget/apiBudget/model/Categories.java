package apiBudget.apiBudget.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
@Valid
public class Categories {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(length=50, nullable = false, unique = true)
   @NotBlank(message = "Le nom ne doit pas être vide !")
   @NotNull(message = "Le nom ne doit pas être null")
   @Size(min = 2 ,message = "Le nom est trop court", max = 225)
   private String nom;


   //une categorie peut appartenir 0 ou plusieurs budgets
   @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
   @JsonIgnore
   private List<Budgets> budgets;
}
