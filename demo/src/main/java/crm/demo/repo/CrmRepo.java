package crm.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.demo.models.Crm;

@Repository
public interface CrmRepo extends JpaRepository<Crm, Long> {

}