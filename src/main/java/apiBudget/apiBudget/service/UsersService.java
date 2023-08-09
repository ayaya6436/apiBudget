package apiBudget.apiBudget.service;

import java.util.List;

import apiBudget.apiBudget.model.Users;

public interface UsersService {
      //Creation user
      String creer(Users users);

      //Avoir la liste des users
      List<Users> lire();
     
      //avoir un use par id_user
      Users lire(Long id);
  
      //Modifier les users
      String modifier(Long id,Users users);
  
      //Supprimer les users
      String supprimer(Long id);
}
