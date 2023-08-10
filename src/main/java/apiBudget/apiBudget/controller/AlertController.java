package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Alertes;
import apiBudget.apiBudget.service.AlerteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("Alert")
public class AlertController {
    @Autowired
    private final AlerteService alerteService;

    @GetMapping("")
    @Operation(summary = "Voir les alertes")
    public List<Alertes> getAllAlert(){
        return alerteService.Lire();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Supprimer une categorie par son ID")
    public String deleteAlert(@Valid @PathVariable Long id){

        return alerteService.supprimer(id);
    }

}
