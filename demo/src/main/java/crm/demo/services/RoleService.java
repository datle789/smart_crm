package crm.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import crm.demo.models.Erole;
import crm.demo.models.Roles;

@Service
public interface RoleService {
  Optional<Roles> findByRoleName(Erole roleName);
}
