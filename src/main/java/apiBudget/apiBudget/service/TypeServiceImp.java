package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Types;
import apiBudget.apiBudget.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TypeServiceImp implements TypeService{
    private TypeRepository typeRepository;

    public TypeServiceImp(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public String creer(Types types) {
        typeRepository.save(types);
        return "Type sauvegarde avec succes";
    }

    @Override
    public List<Types> lire() {
        return  typeRepository.findAll();
    }

    @Override
    public String supprimer(Long id_type) {
        typeRepository.deleteById(id_type);
        return "Vos données ont été surpprimé avec Succès";
    }

}
