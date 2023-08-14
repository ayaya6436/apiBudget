package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.BudgetsPodio;
import apiBudget.apiBudget.model.BudgetsU;
import apiBudget.apiBudget.repository.BudgetsRepository;
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
import java.util.Optional;


@RequestMapping("/budgets")
@AllArgsConstructor
@RestController
@Data
public class BudgetController {
    @Autowired
    private BudgetService budgetService;
    private BudgetsPodioService budgetsPodioService;
    private BudgetServiceImpl budgetservivceImpl;
    private BudgetsRepository budgetsRepository;

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
    public Object modifier(@RequestParam Long id,@RequestBody BudgetsU budgets){
        if (budgets.getId_budgets()!=null){
            Optional<Budgets> budgets1 = budgetsRepository.findByIdAndUsers_Id(budgets.getId_budgets(),id);
            if (budgets1.isPresent()){
                if (budgets.getMontant()!=null){
                    BigDecimal depense = budgetService.depense_total(budgets1.get().getId());
                    if (depense.compareTo(budgets.getMontant()) <=0){
                        budgets1.get().setMontant(budgets.getMontant());
                        budgets1.get().setMontantRestant(budgets.getMontant().subtract(depense));
                        budgetsRepository.save(budgets1.get());
                        return "Modifier avec succes";
                    }else {
                        return "Depense effectuer eleve par rapport a cette montant ";
                    }
                }else {
                    return "Veuillez renseigner le montant";
                }
            }else {
                return "Budgets non desponible";
            }
        }
        return "Budget not found";
    }

    @GetMapping("DEPENSE QUOTIDIEN/{budgetId}")
    public ResponseEntity<Map<LocalDate, BigDecimal>> getDepensequotidien(@Valid @PathVariable Long budgetId){
        Budgets budgets = budgetservivceImpl.getBudgetsById(budgetId);
        Map<LocalDate, BigDecimal> depensequotidient = budgetsPodioService.getDailyExpenses(budgets);
        return  ResponseEntity.ok(depensequotidient);
    }
    @GetMapping("sommebParMois")
    public ResponseEntity <Map<YearMonth, BigDecimal>> getMonthlyBudgetSum(){
        Map<YearMonth, BigDecimal> monthlyBsum = budgetsPodioService.getMonthlyBudgetSums();
        return ResponseEntity.ok(monthlyBsum);
    }
}
