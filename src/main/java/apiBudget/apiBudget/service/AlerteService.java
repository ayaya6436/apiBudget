package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Alertes;

public interface AlerteService {
    Void creer(Alertes alertes);
    String supprimer(Long id);
}
