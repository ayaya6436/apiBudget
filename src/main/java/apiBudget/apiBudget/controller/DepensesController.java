package apiBudget.apiBudget.controller;

import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apiBudget.apiBudget.model.Depenses;
import apiBudget.apiBudget.service.DepensesService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/apiBudget")
public class DepensesController {
    
//Injection de userService
    private final DepensesService depensesService;

    @PostMapping("/depenses")
    public String create(@RequestBody Depenses depenses){
        return depensesService.creer(depenses);
    }

      @GetMapping("/depenses")
    public List<Depenses> read(){
        return depensesService.lire();
    }

    @GetMapping("/depenses/{id}")
    public Depenses read(@PathVariable Long id){
        return depensesService.lire(id);
    } 
      @PatchMapping("/depenses/{id}")
    public Depenses update(@PathVariable Long id, @RequestBody Depenses depenses){
        return depensesService.modifier(id, depenses);
    }

       @DeleteMapping("/depenses/{id}")
    public String delete(@PathVariable Long id){
        return depensesService.supprimer(id);
    }
}
