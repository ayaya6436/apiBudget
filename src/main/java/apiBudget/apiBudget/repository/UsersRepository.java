package apiBudget.apiBudget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import apiBudget.apiBudget.model.Users;

public interface UsersRepository extends JpaRepository<Users,Long>{
    Users findUsersById(Long id);
    
}
