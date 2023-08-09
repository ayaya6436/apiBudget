package apiBudget.apiBudget.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;


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
@Table(name = "alertes")
@Setter
@Getter
@NoArgsConstructor
public class Alertes {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    private String description;

    private Date date_alertes;

//une alerte ne peut etre lie qu'a un et un seul budget
     @ManyToOne
    @JoinColumn(name ="id_budgets",nullable = false)
     @JsonBackReference
    private Budgets budgets;

    
    

}
