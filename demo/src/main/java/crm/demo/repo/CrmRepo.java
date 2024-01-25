package crm.demo.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import crm.demo.models.Crm;

@Repository
public interface CrmRepo extends JpaRepository<Crm, Long> {
    @Query("SELECT c FROM Crm c WHERE c.status = 1 AND c.user.id = :user_id")
    List<Crm> findAllActiveCrms(Long user_id);

    @Query("SELECT c FROM Crm c WHERE (:user_id IS NULL OR c.user.id = :user_id) AND" +
            "(:startDate IS NULL OR c.startDate = :startDate) AND" +
            "(:endDate IS NULL OR c.endDate = :endDate)")
    List<Crm> filterCrms(Long user_id, LocalDate startDate, LocalDate endDate);

}