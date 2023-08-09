package apiBudget.apiBudget.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apiBudget.apiBudget.model.Depenses;
import apiBudget.apiBudget.service.DepensesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/depenses")
@Valid
@Validated
public class DepensesController {
    
//Injection de userService
    private final DepensesService depensesService;

    @PostMapping("/depenses")
    @Operation(summary = "Crée une nouvel depense")
    public String create(@Valid @RequestBody Depenses depenses){
        return depensesService.creer(depenses);
    }

      @GetMapping("/depenses")
      @Operation(summary = "Afficher la liste des depenses")
    public List<Depenses> read(){
        return depensesService.lire();
    }

    @GetMapping("")
    @Operation(summary = "Afficher une depense grace à son ID")
    public Depenses read(@RequestParam @PathVariable Long id){
        return depensesService.lire(id);
    } 
      
    @PutMapping("")
    @Operation(summary = "Récupère une depense grâce à son ID à condition que celui-ci soit en stock!")
    public String update(@RequestParam @PathVariable Long id, @Valid @RequestBody Depenses depenses){
        return depensesService.modifier(id, depenses);
    }

    @DeleteMapping("")
    @Operation(summary = "Supprimer une depense par son ID")

    public String delete(@RequestParam @Valid @PathVariable Long id){
        return depensesService.supprimer(id);
    }
}
