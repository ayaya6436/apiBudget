package apiBudget.apiBudget.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity

public class AlertConditionBoolean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private boolean alertSent1 = false;
    @Column
    private boolean alertSent2 = false;
    @Column
    private boolean alertSent3 = false;

    public AlertConditionBoolean() {
    }

    public AlertConditionBoolean(Long id, boolean alertSent1, boolean alertSent2, boolean alertSent3) {
        this.id = id;
        this.alertSent1 = alertSent1;
        this.alertSent2 = alertSent2;
        this.alertSent3 = alertSent3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAlertSent1() {
        return alertSent1;
    }

    public void setAlertSent1(boolean alertSent1) {
        this.alertSent1 = alertSent1;
    }

    public boolean isAlertSent2() {
        return alertSent2;
    }

    public void setAlertSent2(boolean alertSent2) {
        this.alertSent2 = alertSent2;
    }

    public boolean isAlertSent3() {
        return alertSent3;
    }

    public void setAlertSent3(boolean alertSent3) {
        this.alertSent3 = alertSent3;
    }
}
