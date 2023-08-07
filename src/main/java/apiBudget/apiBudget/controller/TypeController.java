package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.Types;
import apiBudget.apiBudget.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("")
public class TypeController {
    private TypeService typeService;
    @PostMapping("types")
    public Object creer(@RequestBody Types types){
       return typeService.creer(types);
    }

}
