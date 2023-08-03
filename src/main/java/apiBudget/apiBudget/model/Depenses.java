package apiBudget.apiBudget.model;

import java.util.Date;
import java.util.List;

import apiBudget.apiBudget.enums.Type;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "depenses")
@Setter
@Getter
@NoArgsConstructor
public class Depenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_depenses;

    @Column(length = 50)
    private String titre;

    private double montant;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Date date_depenses;

    @Column(length = 250)
    private String note;

//une depense ne peut etre lie qu'a un et un seul user
    @ManyToOne
    @JoinColumn(name ="id_users",nullable = false)
    private Users users;

//une depense ne peut etre lie qu'a un et une seul categories
     @ManyToOne
    @JoinColumn(name ="id_categories",nullable = false)
    private Categories categories;

    //une depense  peut avoir 0 ou plusieurs alertes
   @OneToMany(mappedBy = "depenses", cascade = CascadeType.ALL)
   private List<Alertes> alertes;


}
