package crm.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.demo.models.Erole;
import crm.demo.models.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
  Optional<Roles> findByRoleName(Erole roleName);
}
