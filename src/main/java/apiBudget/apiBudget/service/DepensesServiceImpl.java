package apiBudget.apiBudget.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import apiBudget.apiBudget.model.Depenses;

import apiBudget.apiBudget.repository.DepensesRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepensesServiceImpl implements DepensesService {

    // injection de UsersRepository
    private final DepensesRepository depensesRepository;

    // creation
    @Override
    public String creer(Depenses depenses) {
        // Vérification de la date avant la création de la dépense
        if (isValidDate(depenses.getDate_depenses())) {
            Depenses nouvelDepenses = depensesRepository.save(depenses);
            if (nouvelDepenses != null) {
                return "Depenses créé avec succès";
            } else {
                return "Erreur lors de la création de Depenses.";
            }
        } else {
            return "Date de dépenses invalide. Le jour ne doit pas dépasser 30 et le mois ne doit pas dépasser 12.";
        }
    }

    // Méthode pour vérifier si la date est valide
    private boolean isValidDate(LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        return day <= 30 && month <= 12;
    }

    @Override
    public List<Depenses> lire() {
        return depensesRepository.findAll();

    }

    @Override
    public Depenses lire(Long id_depenses) {
        return depensesRepository.findById(id_depenses).orElse(null);
    }

    @Override
    public Depenses modifier(Long id_depenses, Depenses depenses) {
        return depensesRepository.findById(id_depenses)
                .map(d -> {
                    d.setTitre(depenses.getTitre());
                    d.setMontant(depenses.getMontant());
                    d.setDate_depenses(depenses.getDate_depenses());
                    d.setNote(depenses.getNote());
                    return depensesRepository.save(d);
                }).orElseThrow(() -> new RuntimeException("User non trouve avec l'ID:" + id_depenses));
    }

    @Override
    public String supprimer(Long id_depenses) {
        depensesRepository.deleteById(id_depenses);
        return "depenses supprimer avec succes";
    }

}
