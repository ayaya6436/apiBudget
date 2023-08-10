package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Alertes;

import java.util.List;

public interface AlerteService {
    List<Alertes> Lire();
    String creer(Alertes alertes);
    String supprimer(Long id);

}
