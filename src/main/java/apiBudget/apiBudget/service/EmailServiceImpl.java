package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.EmailDetails;
import apiBudget.apiBudget.repository.EmailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Data

public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("lacinekeita20@gmail.com")
    private String sender;
    @Override
    public String sendSimpleMail(EmailDetails details) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(sender);
            message.setTo(details.getEmail());
            message.setText(details.getMessageBody());
            message.setSubject(details.getSujet());

            //Send message
            javaMailSender.send(message);
            return "Email envoyer avec succès !";

    }catch (Exception e){
        throw new RuntimeException(e.getMessage());

        }}
}
