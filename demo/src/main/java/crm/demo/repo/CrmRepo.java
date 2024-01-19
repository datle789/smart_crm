package crm.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crm.demo.models.Crm;

@Repository
public interface CrmRepo extends JpaRepository<Crm, Long> {
    @Query("SELECT c FROM Crm c WHERE c.status = 1")
    List<Crm> findAllActiveCrms();
}