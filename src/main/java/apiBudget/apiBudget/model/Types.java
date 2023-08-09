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
import lombok.Data;

@Entity
@Table(name = "types")
@Data
@Valid
public class Types {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250, unique = true)
    @NotNull(message = "Le libelle ne doit pas être null !")
    @NotBlank(message = "Le libelle ne doit pas être vide !")
    private String libelle;


   //un type peut etre lie a 1 ou plusieurs depenses
   @OneToMany(mappedBy = "types", cascade = CascadeType.ALL)
   @JsonIgnore
   
   private List<Depenses> depenses;

    public Types() {
    }

    public Types(Long id, String libelle, List<Depenses> depenses) {
        this.id = id;
        this.libelle = libelle;
        this.depenses = depenses;
    }
}
