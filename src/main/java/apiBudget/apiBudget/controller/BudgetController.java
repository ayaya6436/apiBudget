package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.BudgetsPodio;
import apiBudget.apiBudget.service.BudgetService;
import apiBudget.apiBudget.service.BudgetServiceImpl;
import apiBudget.apiBudget.service.BudgetsPodioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;


@RequestMapping("/budgets")
@AllArgsConstructor
@RestController
@Data
public class BudgetController {
    @Autowired
    private BudgetService budgetService;
    private BudgetsPodioService budgetsPodioService;
    private BudgetServiceImpl budgetservivceImpl;

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

    @GetMapping("DEPENSE_QUOTIDIEN/{budgetId}")
    @Operation(summary = "La liste des dépenses quotidien par budget")
    public ResponseEntity<Map<LocalDate, BigDecimal>> getDepensequotidien(@Valid @PathVariable Long budgetId){
        Budgets budgets = budgetservivceImpl.getBudgetsById(budgetId);
        Map<LocalDate, BigDecimal> depensequotidient = budgetsPodioService.getDailyExpenses(budgets);
        return  ResponseEntity.ok(depensequotidient);
    }
    @GetMapping("sommeBparMois")
    @Operation(summary = "Affiche la somme des budgets par mois")

    public ResponseEntity <Map<YearMonth, BigDecimal>> getMonthlyBudgetSum(){
        Map<YearMonth, BigDecimal> monthlyBsum = budgetsPodioService.getMonthlyBudgetSums();
        return ResponseEntity.ok(monthlyBsum);
    }
}
