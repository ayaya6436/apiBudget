package apiBudget.apiBudget.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
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
        try {
            usersRepository.save(users);
        }catch (DataAccessException e){
            return "Donnee incorrecte";
        }
            return "Utilisateur créé avec succès";
    }
    

    // avoir tout les users
    @Override
    public List<Users> lire() {
         List<Users> usersList = usersRepository.findAll();

        if (usersList.isEmpty()) {
            System.out.println("La liste est vide.");
        }

        return usersList;
    }
    // avoir un user par id

    @Override
    public Users lire(Long id) {
        return usersRepository.findById(id).orElseThrow(()->new RuntimeException("Utilisateur non trouve"));
    }

    // modifier un user par id

    @Override
    public String modifier(Long id, Users users) {
            Optional<Users> existingUsers = usersRepository.findById(id);

            if(existingUsers.isPresent()) {
                Users u = existingUsers.get();
                    u.setNom(users.getNom());
                    u.setPrenom(users.getPrenom());
                    u.setEmail(users.getEmail());
                    u.setPassword(users.getPassword());
                    usersRepository.save(u);
                    return "User modifiée avec succès";
            }else {
        throw new NoSuchElementException("User non trouvée avec l'ID: " + id);
    }
        
               
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
