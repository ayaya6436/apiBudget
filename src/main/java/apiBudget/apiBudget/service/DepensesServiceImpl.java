package apiBudget.apiBudget.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.Depenses;
import apiBudget.apiBudget.repository.BudgetsRepository;
import apiBudget.apiBudget.repository.DepensesRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepensesServiceImpl implements DepensesService {

    // Injection du référentiel DepensesRepository
    private final DepensesRepository depensesRepository;
    private final BudgetsRepository budgetsRepository; // Injection du référentiel Budgets

    // Création
    @Override
    public String creer(Depenses depenses) {

        // Vérification de la date avant la création de la dépense
        if (isValidDate(depenses.getDate_depenses())) {
                    // Vérification du montant du budget

            Budgets budget = budgetsRepository.findById(depenses.getBudgets().getId()).orElse(null);
            if (budget != null) {
                double totalDepenses = budget.getDepenses().stream().mapToDouble(Depenses::getMontant).sum();
                double montantRestant = budget.getMontant() - totalDepenses;
                if (depenses.getMontant() > montantRestant) {
                    return "Le montant de la dépense dépasse le montant restant du budget:"+ montantRestant + "FCFA";
                }

                Depenses nouvelDepenses = depensesRepository.save(depenses);
                if (nouvelDepenses != null) {
                    return "Dépenses créée avec succès. ResteBudget ="+ montantRestant + "FCFA";
                } else {
                    return "Erreur lors de la création des Dépenses.";
                }
            } else {
                return "Budget non trouvé. Veuillez spécifier un budget valide pour la dépense.";
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
    public Depenses lire(Long id) {
        return depensesRepository.findById(id).orElse(null);
    }

    @Override
    public Depenses modifier(Long id, Depenses depenses) {
        return depensesRepository.findById(id)
                .map(d -> {
                    d.setTitre(depenses.getTitre());
                    d.setMontant(depenses.getMontant());
                    d.setDate_depenses(depenses.getDate_depenses());
                    d.setNote(depenses.getNote());
                    return depensesRepository.save(d);
                }).orElseThrow(() -> new RuntimeException("User non trouve avec l'ID:" + id));
    }

    @Override
    public String supprimer(Long id) {
        depensesRepository.deleteById(id);
        return "depenses supprimer avec succes";
    }

}
