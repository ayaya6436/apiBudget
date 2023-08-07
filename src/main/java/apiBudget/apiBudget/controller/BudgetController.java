package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.BudgetsPodio;
import apiBudget.apiBudget.service.BudgetService;
import apiBudget.apiBudget.service.BudgetsPodioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("budgets")
@AllArgsConstructor
@RestController
public class BudgetController {
    private BudgetService budgetService;
    private BudgetsPodioService budgetsPodioService;
    @PostMapping("")
    public String creer(@RequestBody BudgetsPodio budgetsPodio1){

        return budgetsPodioService.creer(budgetsPodio1);
    }
    @GetMapping("")
    public Object lire(@RequestParam Long id){
        return budgetService.lire(id);
    }
    @PatchMapping("")
    public Object modifier(@RequestParam Long id,@RequestBody Budgets budgets){
        return budgetService.modifier(id,budgets);
    }
}
