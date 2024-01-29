package crm.demo.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import crm.demo.models.Roles;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Integer> {

}
