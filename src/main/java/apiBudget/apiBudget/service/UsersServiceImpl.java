package apiBudget.apiBudget.service;

import java.util.List;

import org.springframework.stereotype.Service;

import apiBudget.apiBudget.model.Users;
import apiBudget.apiBudget.repository.UsersRepository;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

     // injection de UsersRepository
     private final UsersRepository usersRepository;

    // creation
    @Override
    public Users creer(Users users) {
        return usersRepository.save(users);
    }

    // avoir tout les users
    @Override
    public List<Users> lire() {
        return usersRepository.findAll();
    }
    // avoir un user par id

    @Override
    public Users lire(Long id_users) {
        return usersRepository.findById(id_users).orElse(null);
    }

    // modifier une quiz par id_user

    @Override
    public Users modifier(Long id_users, Users users) {
        return usersRepository.findById(id_users)
                .map(u -> {
                    u.setNom(users.getNom());
                    u.setPrenom(users.getPrenom());
                    u.setEmail(users.getEmail());
                    u.setPassword(users.getPassword());
                    return usersRepository.save(u);
                }).orElseThrow(() -> new RuntimeException("User non trouve avec l'ID:" + id_users));
    }

    // supprimer une quiz par id_user

    @Override
    public String supprimer(Long id_users) {
        usersRepository.deleteById(id_users);
        return "user supprimer avec succes";
    }
}
