package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Types;
import apiBudget.apiBudget.service.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/Type")
public class TypeController {
   private final TypeService typeService;

    @PostMapping("/create")
    @Operation(summary = "Crée un nouveau catégorie")
    public String create(@Valid @RequestBody Types types){
        return typeService.creer(types);
    }
    // LA METHODE READ
    @GetMapping("/read")
    @Operation(summary = "Afficher la liste des types")
    public List<Types> read(){
        return typeService.Lire();
    }

    @GetMapping("/read/{id}")
    @Operation(summary = "Afficher un type grace à son ID")
    public Types read(@Valid @PathVariable Long id ){
        return typeService.getTypesById(id);
    }

    // LA METHODE UPDATE
    @Operation(summary = "Récupère un type grâce à son ID à condition que celui-ci soit en stock!")
    @PutMapping("/update/{id}")
    public Types update(@PathVariable Long id,@Valid @RequestBody Types types){
        return typeService.modifier(id, types);
    }
    // LA METHODE DELETE
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Supprimer un type par son ID")
    public String delete (@Valid @PathVariable Long id){

        return typeService.supprimer(id);
    }


}
