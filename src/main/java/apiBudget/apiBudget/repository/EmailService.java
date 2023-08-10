package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
}
