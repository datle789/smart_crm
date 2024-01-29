package crm.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.demo.models.Roles;
import crm.demo.models.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

}
