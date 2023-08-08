package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Categories;
import apiBudget.apiBudget.service.CategoriesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/Categorie")
public class CategoriesController {
    private final CategoriesService categoriesService;

    // LA METHODE CREATE
    @PostMapping("/create")
    @Operation(summary = "Crée un nouveau catégorie")
    public String create(@Valid @RequestBody Categories categories){
        return categoriesService.creer(categories);
    }
    // LA METHODE READ
    @GetMapping("/read")
    @Operation(summary = "Afficher la liste catégorie")
    public List<Categories> read(){
        return categoriesService.Lire();
    }

    @GetMapping("/read/{id}")
    @Operation(summary = "Afficher une catégorie grace à son ID")
    public Categories read(@Valid @PathVariable Long id ){
        return categoriesService.getCategorieById(id);
    }

    // LA METHODE UPDATE
    @Operation(summary = "Récupère une catégorie grâce à son ID à condition que celui-ci soit en stock!")
    @PutMapping("/update/{id}")
    //@ApiOperation("Modifier un utilisateur par son ID")
    public Categories update(@PathVariable Long id,@Valid @RequestBody Categories categories){
        return categoriesService.modifier(id, categories);
    }
    // LA METHODE DELETE
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Supprimer une categorie par son ID")
    public String delete (@Valid @PathVariable Long id){

        return categoriesService.supprimer(id);
    }


}
