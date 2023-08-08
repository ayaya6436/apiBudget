package apiBudget.apiBudget.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "categories")
@Data
@Valid
public class Categories {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(length=50, nullable = false)
   @NotEmpty(message = "Le nom ne doit pas être vide !")
   @NotBlank(message = "Le nom ne doit pas être vide !")
   private String nom;


   //une categorie peut appartenir 0 ou plusieurs budgets
   @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
   @JsonIgnore
   private List<Budgets> budgets;
}
