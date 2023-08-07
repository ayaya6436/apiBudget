package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Types;

public interface TypeService {
    String creer(Types types);
    Types lire(Long id_type);
    String supprimer(Long id_type);


}
