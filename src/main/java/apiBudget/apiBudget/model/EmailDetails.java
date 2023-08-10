package apiBudget.apiBudget.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {
    private String email;
    private String messageBody;
    private String sujet;
}
