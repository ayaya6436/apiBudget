package apiBudget.apiBudget.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import apiBudget.apiBudget.model.Alertes;
import apiBudget.apiBudget.model.EmailDetails;
import apiBudget.apiBudget.repository.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmailServiceImpl emailServiceIplm;
    private AlerteService alerteService;

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
                String msg = "Votre Budget est de " +budget.getMontant()+ " FCFA pour une depense en "+depenses.getTitre()+
                        "et votre montant restant est " + montantRestant;
                EmailDetails details = new EmailDetails(budget.getUsers().getEmail(), msg, "Details du depense");
                emailServiceIplm.sendSimpleMail(details);
                LocalDate dateToday = LocalDate.now();

                Alertes alertes = new Alertes();
                alertes.setDate_alertes(dateToday);
                alertes.setDescription(msg);
                alertes.setBudgets(budget);
                alerteService.creer(alertes);




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
