package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.*;
import apiBudget.apiBudget.repository.AlertesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class AlerteServiceImpl implements AlerteService {
    private AlertesRepository alertesRepository;

    public AlerteServiceImpl(AlertesRepository alertesRepository) {
        this.alertesRepository = alertesRepository;
    }

    @Override
    public List<Alertes> Lire() {
        return alertesRepository.findAll();
    }

    @Override
    public String creer(Alertes alertes) {
         alertesRepository.save(alertes);
         return "Alert Envoyer avec succès !";
    }

    @Override
    public String supprimer(Long id) {
        Alertes alertes = alertesRepository.findById(id).orElseThrow(()-> new RuntimeException("Alert non trouvé !"));

        if (alertes != null ){
            alertesRepository.deleteById(alertes.getId());
            return "Alert supprimer avec succès !";
        }
        if (alertes == null){
            return "Utilisateur non trouvé !";
        }
        return null;
    }
}
