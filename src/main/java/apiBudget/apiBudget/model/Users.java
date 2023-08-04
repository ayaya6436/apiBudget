package apiBudget.apiBudget.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class Users {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_users;

   @Column(length=50)
   private String nom;

   @Column(length=50)
   private String prenom;

   @Column(length=50)
   private String email;
   
   @Column(length=250)
   private String password;

   //un user peut effectuer 0 ou plusieurs depenses
   @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
   private List<Depenses> depenses;


   //un user peut definir 1 ou plusieurs budget
   @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
   private List<Budgets> budgets;

   //un user  peut avoir 0 ou plusieurs alertes
   @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
   private List<Alertes> alertes;
}
