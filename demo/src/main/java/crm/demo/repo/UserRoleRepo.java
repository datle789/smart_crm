package crm.demo.repo;

import crm.demo.models.Crm;
import crm.demo.models.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

}
