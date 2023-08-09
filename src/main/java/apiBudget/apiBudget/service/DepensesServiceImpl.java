package apiBudget.apiBudget.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public String creer(Depenses depenses) {
        if (!isValidDate(depenses.getDate_depenses())) {
            return "Date de dépenses invalide. Le jour ne doit pas dépasser 30 et le mois ne doit pas dépasser 12.";
        }

        Budgets budget = budgetsRepository.findById(depenses.getBudgets().getId()).orElse(null);
        if (budget == null) {
            return "Budget non trouvé. Veuillez spécifier un budget valide pour la dépense.";
        }

        BigDecimal totalDepenses = budget.getDepenses()
                .stream()
                .map(Depenses::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal montantRestant = BigDecimal.valueOf(budget.getMontant()).subtract(totalDepenses);

        if (depenses.getMontant().compareTo(montantRestant) > 0) {
            return "Le montant de la dépense dépasse le montant restant du budget: " + montantRestant + " FCFA";
        }

        montantRestant = montantRestant.subtract(depenses.getMontant());

        // Ajouter la nouvelle dépense à la liste des dépenses du budget
        

        try {
            budgetsRepository.save(budget);

            // Mise à jour de montantRestant après avoir inséré la première dépense
            Depenses nouvelDepenses = depensesRepository.save(depenses);
            if (nouvelDepenses != null) {
                return "Dépense créée avec succès. ResteBudget =" + montantRestant + " FCFA";
            } else {
                return "Erreur lors de la création des Dépenses.";
            }
        } catch (Exception e) {
            return "Une erreur est survenue lors de la création de la dépense : " + e.getMessage();
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
        List<Depenses> depensesList = depensesRepository.findAll();

        if (depensesList.isEmpty()) {
            System.out.println("La liste est vide.");
        }

        return depensesList;

    }

    @Override
    public Depenses lire(Long id) {
        return depensesRepository.findById(id).orElseThrow(()-> new RuntimeException("depenses non trouvé !"));
    }

    @Override
   public String modifier(Long id, Depenses depenses) {
    Optional<Depenses> existingDepense = depensesRepository.findById(id);

    if (existingDepense.isPresent()) {
        Depenses d = existingDepense.get();
        d.setTitre(depenses.getTitre());
        d.setMontant(depenses.getMontant());
        d.setDate_depenses(depenses.getDate_depenses());
        d.setNote(depenses.getNote());
        depensesRepository.save(d);
        return "Dépense modifiée avec succès";
    } else {
        throw new NoSuchElementException("Dépense non trouvée avec l'ID: " + id);
    }
}

    @Override
    public String supprimer(Long id) {
        Depenses depenses = depensesRepository.findById(id).orElseThrow(()-> new RuntimeException("Depenses non trouvé !"));

        if (depenses != null ){
            depensesRepository.deleteById(depenses.getId());
            return "Depense supprimer avec succès !";
        }
        if (depenses == null){
            return "Depense non trouvé !";
        }
        return null;
    }
}


