package apiBudget.apiBudget.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

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

import apiBudget.apiBudget.model.Users;
import apiBudget.apiBudget.service.UsersService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")

@Valid
@Validated
public class UsersController {

//Injection de userService
    private final UsersService usersService;

    @PostMapping("")
    @Operation(summary = "Creer un nouveau user")
    public String create(@Valid @RequestBody Users users){
        return usersService.creer(users);
    }

      @GetMapping("/users")
      @Operation(summary = "Afficher la liste des users")
    public List<Users> read(){
        return usersService.lire();
    }

    @GetMapping("")
    @Operation(summary = "Récupère une user grâce à son ID à condition que celui-ci soit en stock!")
    public Users read(@Valid @RequestParam @PathVariable Long id){
        return usersService.lire(id);
    } 

    @PutMapping("")
    @Operation(summary = "Récupère un user grâce à son ID à condition que celui-ci soit en stock!")
    public String update(@RequestParam @Valid @PathVariable Long id, @RequestBody Users users){
        return usersService.modifier(id, users);
    }

       @DeleteMapping("")
    @Operation(summary = "Supprimer une depense par son ID")

    public String delete(@Valid @RequestParam @PathVariable Long id){
        return usersService.supprimer(id);
    }
}
