package apiBudget.apiBudget.service;

import java.util.List;

import apiBudget.apiBudget.model.Depenses;


public interface DepensesService {
     //Creation depense
      String creer(Depenses depenses);

      //Avoir la liste des depenses
      List<Depenses> lire();
     
      //avoir une depense par id_user
      Depenses lire(Long id);
  
      //Modifier les depenses
      Depenses modifier(Long id,Depenses depenses);
  
      //Supprimer les depenses
      String supprimer(Long id);
}
