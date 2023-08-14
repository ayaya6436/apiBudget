package apiBudget.apiBudget.repository;

import apiBudget.apiBudget.model.Budgets;
import apiBudget.apiBudget.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetsRepository extends JpaRepository<Budgets,Long> {

    List<Budgets> findAllByFinAfterOrFinEqualsAndUsers_IdAndCategories_Id(LocalDate date,LocalDate date1,Long id1,Long id2);

    List<Budgets> findAllByUsers_IdOrderByFinDesc(Long id);

    @Query(value = "SELECT * from budgets" +
            " WHERE (debut < :today OR debut = :today)" +
            " AND id_categories = :id_categories" +
            " AND id_users =:id_users" +
            " ORDER By debut DESC" ,nativeQuery = true)
    List<Budgets> findoldbudget(@Param("today") LocalDate today,@Param("id_users") Long id_users,@Param("id_categories") Long id_categorie);

    @Query(value = "SELECT * FROM budgets " +
            "WHERE (debut < :dateAujourdhui OR debut = :dateAujourdhui) " +
            "AND (fin > :dateAujourdhui OR fin = :dateAujourdhui) " +
            "AND id_users = :id_utilisateur", nativeQuery = true)
    List<Budgets> findAllBycurrentbudget(@Param("dateAujourdhui") LocalDate dateAujourdhui,@Param("id_utilisateur") Long id_utilisateur);

    //List<Budgets> findAllByDebutBeforeOrDebutEqualsAndFinAfterOrFinEqualsAndUsers_Id(@Param("date1") LocalDate date1,@Param("date2") LocalDate date2,@Param("date3")LocalDate date3,@Param("date4") LocalDate date4,@Param("id") Long id);

    @Query(value = "SELECT DISTINCT c.id " +
            "FROM categories c " +
            "JOIN budgets b ON c.id = b.id_categories " +
            "WHERE b.id_users = :id", nativeQuery = true)
    List<Long> findDistinctByUsers_Id(@Param("id") Long id);

    Optional<Budgets> findByIdAndUsers_Id(Long id, Long idu);


@Query(value = "SELECT * from budgets" +
        " where (debut < :todays or debut = :todays)" +
        " and (fin > :todays or fin = :todays)" +
        " and id_users = :id1" +
        " and id_categories = :id2",nativeQuery = true)
    Budgets findcurrentbudget(@Param("todays") LocalDate todays,@Param("id1") Long id1,@Param("id2") Long id2);


}
