package apiBudget.apiBudget.controller.advice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;

import apiBudget.apiBudget.dto.ErrorEntity;

@ControllerAdvice
public class ApplicationControllerAdvice {

    // Gestionnaire pour DateTimeParseException 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DateTimeParseException.class })
    public @ResponseBody ErrorEntity handleDateTimeParseException(DateTimeParseException exception) {
        String errorMessage = exception.getMessage();

        // Vérifier si l'erreur est due à un jour invalide
        if (errorMessage.contains("Invalid value for DayOfMonth")) {
            errorMessage = "Date de dépenses invalide. Le jour ne doit pas dépasser 31.";
        }
        // Vérifier si l'erreur est due à un mois invalide
        else if (errorMessage.contains("Invalid value for MonthOfYear")) {
            errorMessage = "Date de dépenses invalide. Le mois ne doit pas dépasser 12.";
        } else {
            errorMessage = "Format de date invalide. Veuillez utiliser le format \"dd-MM-yyyy\".";
        }

        return new ErrorEntity(errorMessage);
    }


    
}
