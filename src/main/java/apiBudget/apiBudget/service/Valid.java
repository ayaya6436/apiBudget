package apiBudget.apiBudget.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class Valid {
    public static Boolean dates(String date){
        LocalDate date1 ;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int days = Integer.parseInt(date.substring(0,2));
        try {
            date1 = LocalDate.parse(date,formatter);
        }
        catch (DateTimeParseException e){
            return false;
        }
        YearMonth yearMonth = YearMonth.of(date1.getYear(),date1.getMonth());
        if (days <= yearMonth.lengthOfMonth()){
            return true;
        }else {
            return false;
        }
    }
}
