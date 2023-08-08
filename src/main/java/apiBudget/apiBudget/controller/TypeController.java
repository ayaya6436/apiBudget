package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Types;
import apiBudget.apiBudget.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("")
public class TypeController {
    private TypeService typeService;
    @PostMapping("create")
    public Object creer(@RequestBody Types types){
       return typeService.creer(types);
    }
    @GetMapping("read")
    public List<Types> read(){
        return typeService.lire();
    }


}
