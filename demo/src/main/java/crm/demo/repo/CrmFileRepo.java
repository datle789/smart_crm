package crm.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import crm.demo.models.CrmFile;

@Repository
public interface CrmFileRepo extends JpaRepository<CrmFile, Long> {
    @Query("SELECT cf FROM CrmFile cf WHERE cf.crm.id = :crmId")
    List<CrmFile> findAllByCrmId(long crmId);
}
