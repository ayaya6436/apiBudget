package apiBudget.apiBudget.controller;

import apiBudget.apiBudget.model.EmailDetails;
import apiBudget.apiBudget.repository.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sendEmail")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("send")
    public String sendEmail(@RequestBody EmailDetails details){
        return emailService.sendSimpleMail(details);
    }

}
