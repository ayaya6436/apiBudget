package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.BudgetsPodio;
import apiBudget.apiBudget.service.BudgetService;
import apiBudget.apiBudget.service.BudgetsPodioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/budgets")
@AllArgsConstructor
@RestController
public class BudgetController {
    private BudgetService budgetService;
    private BudgetsPodioService budgetsPodioService;
    @PostMapping("")
    @Operation(summary = "Enregistrer un budget pour chaque categorie")
    public String creer(@RequestBody BudgetsPodio budgetsPodio1){

        return budgetsPodioService.creer(budgetsPodio1);
    }
    @GetMapping("")
    @Operation(summary = "Lire l'ensemble des budgets par categorie")
    public Object lire(@RequestParam Long id , @RequestParam int choix){

        return budgetsPodioService.lire(id,choix);

    }
    @PatchMapping("")
    public Object modifier(@RequestParam Long id,@RequestBody Budgets budgets){
        Long idu= 0L;
        return budgetService.modifier(id,idu,budgets);
    }
}
