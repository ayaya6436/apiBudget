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
    public String creer(Users users) {
        Users nouvelUtilisateur = usersRepository.save(users);
        if (nouvelUtilisateur != null) {
            return "Utilisateur créé avec succès";
        } else {
            return "Erreur lors de la création de l'utilisateur.";
        }
    }
    

    // avoir tout les users
    @Override
    public List<Users> lire() {
        return usersRepository.findAll();
    }
    // avoir un user par id

    @Override
    public Users lire(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    // modifier un user par id

    @Override
    public Users modifier(Long id, Users users) {
        return usersRepository.findById(id)
                .map(u -> {
                    u.setNom(users.getNom());
                    u.setPrenom(users.getPrenom());
                    u.setEmail(users.getEmail());
                    u.setPassword(users.getPassword());
                    return usersRepository.save(u);
                }).orElseThrow(() -> new RuntimeException("User non trouve avec l'ID:" + id));
    }

    // supprimer une user par id_user

    @Override
    public String supprimer(Long id) {
       Users nouvelUtilisateur = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User non trouve avec l'ID:" + id));
        if (nouvelUtilisateur != null) {
            usersRepository.deleteById(id);
            return "Utilisateur supprimer avec succès";
        } else {
            return "L'utilisateur n'existe pas.";
        }
    }
}
