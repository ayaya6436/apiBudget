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
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
public class Categories {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id_categories;

   @Column(length=50)
   private String nom;


   //une categorie peut appartenir 0 ou plusieurs depenses
   @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
   private List<Depenses> depenses;
}
