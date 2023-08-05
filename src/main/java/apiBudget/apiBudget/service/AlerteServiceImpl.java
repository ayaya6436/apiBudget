package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Alertes;
import apiBudget.apiBudget.repository.AlertesRepository;
import org.springframework.stereotype.Service;

@Service
public class AlerteServiceImpl implements AlerteService {
    private AlertesRepository alertesRepository;

    public AlerteServiceImpl(AlertesRepository alertesRepository) {
        this.alertesRepository = alertesRepository;
    }

    @Override
    public Void creer(Alertes alertes) {
        alertesRepository.save(alertes);

        return null;
    }

    @Override
    public String supprimer(Long id) {
        alertesRepository.deleteById(id);
        return "Donnee supprimer avec succes";
    }
}
