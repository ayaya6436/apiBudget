package apiBudget.apiBudget.service;


import apiBudget.apiBudget.model.Types;
import apiBudget.apiBudget.repository.TypeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data

public class TypeServiceImp implements TypeService{
    @Autowired
   private TypeRepository typeRepository;

    @Override
    public String creer(Types types) {
        Types type1 = typeRepository.getTypeByLibelle(types.getLibelle());
        if(type1 == null){
            typeRepository.save(types);
            return "Type "+types.getLibelle()+" creer avec succès !";

        } else if (type1 != null) {
            return "Un type avec le libeller : "+type1.getLibelle()+" existe déjà !";

        }
        return "Un type avec le libeller : "+type1.getLibelle()+" existe déjà !";
    }

    @Override
    public List<Types> Lire() {

        List<Types> typesList = typeRepository.findAll();

        if (typesList.isEmpty()) {
            System.out.println("Il n'ya pas de types disponible d'abord !");
        }

        return typesList;
    }

    @Override
    public Types getTypesById(Long id) {
        return typeRepository.findById(id).orElseThrow(()-> new RuntimeException("Type non trouvé avec cet id: "+id));
    }

    @Override
    public Types modifier(Long id, Types types) {
        return typeRepository.findById(id).map(type ->{

            type.setLibelle(types.getLibelle());
            type.setDepenses(types.getDepenses());

            return typeRepository.save(type);
        }).orElseThrow(()-> new RuntimeException("Type non trouvé avec cet id: "+id));
    }

    @Override
    public String supprimer(Long id) {
        Types types = typeRepository.findById(id).orElseThrow(()-> new RuntimeException("Type non trouvé !"));

        if (types != null ){
            typeRepository.deleteById(types.getId());
            return "Type "+types.getLibelle()+" a été supprimer avec succès !";
        }
        if (types == null){
            return "Type non trouvé !";
        }
        return "Type non trouvé !";
    }
}
