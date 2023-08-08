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
import jakarta.validation.Valid;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "types")
@Data
public class Types {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250 , nullable = false)
    @Valid
    private String libelle;


   //un type peut etre lie a 1 ou plusieurs depenses
   @OneToMany(mappedBy = "types", cascade = CascadeType.ALL)
   private List<Depenses> depenses;

    public Types() {
    }

    public Types(Long id, String libelle, List<Depenses> depenses) {
        this.id = id;
        this.libelle = libelle;
        this.depenses = depenses;
    }
}
