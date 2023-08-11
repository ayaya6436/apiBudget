package apiBudget.apiBudget.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.validation.constraints.Email;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({ "depenses", "budgets" })
public class Users {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(length=50, nullable = false, unique = true)
   @NotBlank(message = "Le nom ne doit pas être vide !")
   @NotNull(message = "Le nom ne doit pas être null")
   @Size(min = 3 ,message = "Le nom est trop court", max = 225)
   private String nom;

   @Column(length=50, nullable = false, unique = true)
   @NotBlank(message = "Le prenom ne doit pas être vide !")
   @NotNull(message = "Le prenom ne doit pas être null")
   @Size(min = 3 ,message = "Le nom est trop court", max = 225)
   private String prenom;

   @Column(length = 50)
   @Email(message = "L'adresse email doit être valide")
   @NotBlank(message = "L'adresse email ne doit pas être vide")
   private String email;
   
   @Column(length=250)
   private String password;

   //un user peut effectuer 0 ou plusieurs depenses
   
   @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
   @JsonManagedReference

   private List<Depenses> depenses;


   //un user peut definir 1 ou plusieurs budget
   @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
   @JsonIgnore
   private List<Budgets> budgets;

   
}
