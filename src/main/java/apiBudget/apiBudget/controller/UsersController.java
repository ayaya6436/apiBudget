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
    public String create(@RequestBody Users users){
        return usersService.creer(users);
    }

      @GetMapping("/users")
    public List<Users> read(){
        return usersService.lire();
    }

    @GetMapping("/users/{id_users}")
    public Users read(@PathVariable Long id_users){
        return usersService.lire(id_users);
    } 
      @PatchMapping("/users/{id_users}")
    public Users update(@PathVariable Long id_users, @RequestBody Users users){
        return usersService.modifier(id_users, users);
    }

       @DeleteMapping("/users/{id_users}")
    public String delete(@PathVariable Long id_users){
        return usersService.supprimer(id_users);
    }
}
