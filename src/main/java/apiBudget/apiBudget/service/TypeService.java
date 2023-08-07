package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Types;

public interface TypeService {
    Types lire(Long id_type);

    String supprimer(Long id_type);

    String creer(Types types);
}