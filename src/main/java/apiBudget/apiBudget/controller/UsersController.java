package apiBudget.apiBudget.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apiBudget.apiBudget.model.Users;
import apiBudget.apiBudget.service.UsersService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/apiBudget")


public class UsersController {

//Injection de userService
    private final UsersService usersService;

    @PostMapping("/users")
    @Operation(summary = "Creer un user")
    public String create(@RequestBody Users users){
        return usersService.creer(users);
    }

      @GetMapping("/users")
    public List<Users> read(){
        return usersService.lire();
    }

    @GetMapping("/users/{id}")
    public Users read(@PathVariable Long id){
        return usersService.lire(id);
    } 
      @PatchMapping("/users/{id}")
    public Users update(@PathVariable Long id, @RequestBody Users users){
        return usersService.modifier(id, users);
    }

       @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        return usersService.supprimer(id);
    }
}
