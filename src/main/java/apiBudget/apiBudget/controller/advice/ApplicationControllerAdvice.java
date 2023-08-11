package apiBudget.apiBudget.controller.advice;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;

import apiBudget.apiBudget.dto.ErrorEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;



@RestControllerAdvice
public class ApplicationControllerAdvice {

    // Gestionnaire pour DateTimeParseException 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ DateTimeParseException.class })
    public @ResponseBody @Valid ErrorEntity handleDateTimeParseException(DateTimeParseException exception) {
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


    // runtime exception
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({RuntimeException.class})
    public @ResponseBody @Valid ErrorEntity handleRuntimeException(RuntimeException exception){
        String errorMessage = exception.getMessage();

        // Vérifier si l'erreur est due à un jour invalide
        if (errorMessage.contains("Catégorie non trouvé !")) {
            errorMessage = "Catégorie non trouvé !";
        }
        return new ErrorEntity( exception.getMessage());
    }

    // Not Found Exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntityNotFoundException.class})
    public @ResponseBody @Valid ErrorEntity handleException(EntityNotFoundException exception){
        // String errorMessage = exception.getMessage();
        return new ErrorEntity(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public @Valid
    @ResponseBody ErrorEntity handleHttpServerErrorException(HttpServerErrorException e){
    return new ErrorEntity(e.getMessage());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public @Valid
    @ResponseBody ErrorEntity handleHttpClientErrorException(HttpClientErrorException e) {
        // Log the error
        //logger.error(e);

        // Return a 404 Not Found response with a custom message
        return new  ErrorEntity(e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    public@Valid
    @ResponseBody ErrorEntity handleAllException(Exception exception){
        return new ErrorEntity(exception.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request){
        final  String[] msg = {""};
        exception.getBindingResult().getAllErrors().forEach((error)-> {
            msg[0] += "'"+((FieldError) error).getField()+"' : "+error.getDefaultMessage()+", ";
        });
        return new ErrorEntity(
                msg[0]
        );
    }
}
