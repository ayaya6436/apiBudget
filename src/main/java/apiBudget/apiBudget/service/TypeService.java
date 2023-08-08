package apiBudget.apiBudget.service;
import apiBudget.apiBudget.model.Types;

import java.util.List;

public interface TypeService {
    String creer(Types types);
    List<Types> Lire();
    Types getTypesById(Long id);
    Types modifier(Long id, Types types);
    String supprimer(Long id);
}