package apiBudget.apiBudget.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    private Long id_budgets;
    private Date periode;
    private double montant;

//un budget ne peut etre lie qu'a un et un seul user
    @ManyToOne()
    @JoinColumn(name = "id_users",nullable = false)
    private Users users;

//une categorie ne peut etre lie qu'a un et un seul budget
    @OneToOne()
    @JoinColumn(name = "id_categories",nullable = false)
    private Categories categories;

}
