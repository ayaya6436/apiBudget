package apiBudget.apiBudget.controller.advice;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import apiBudget.apiBudget.dto.ErrorEntity;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ApplicationControllerAdvice {
    // Gestionnaire pour EntityNotFoundException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ EntityNotFoundException.class })
    public @ResponseBody ErrorEntity handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ErrorEntity(exception.getMessage());
    }

    // Gestionnaire pour DateTimeParseException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DateTimeParseException.class })
    public @ResponseBody ErrorEntity handleDateTimeParseException(DateTimeParseException exception) {
        String errorMessage = exception.getMessage();

        // Vérifier si l'erreur est due à un DayOfMonth invalide
        if (errorMessage.contains("Jour Invalide")) {
            errorMessage = "Date de dépenses invalide. Le jour ne doit pas dépasser 31.";
        }
        // Vérifier si l'erreur est due à un Month invalide
        else if (errorMessage.contains("Mois Invalide")) {
            errorMessage = "Date de dépenses invalide. Le mois ne doit pas dépasser 12.";
        } else {
            errorMessage = "Format de date invalide. Veuillez utiliser le format \"dd-MM-yyyy\".";
        }

        return new ErrorEntity(errorMessage);
    }

}
