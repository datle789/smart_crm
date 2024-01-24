package crm.demo.ServiceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crm.demo.models.Erole;
import crm.demo.models.Roles;
import crm.demo.repo.RoleRepository;
import crm.demo.services.RoleService;

@Service
public class RoleServiceImp implements RoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Optional<Roles> findByRoleName(Erole roleName) {
    return roleRepository.findByRoleName(roleName);
  }

}
