package apiBudget.apiBudget.service;

import apiBudget.apiBudget.model.Types;
import apiBudget.apiBudget.repository.TypeRepository;

public class TypeServiceImpl implements TypeService{
    private TypeRepository typeRepository;
    @Override
    public Types creer(Types types) {
        return typeRepository.save(types);
    }
}
