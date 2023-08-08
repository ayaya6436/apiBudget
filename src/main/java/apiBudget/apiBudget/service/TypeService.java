package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Types;

import java.util.List;

public interface TypeService {
    List <Types>  lire();

    String supprimer(Long id_type);

    String creer(Types types);
}