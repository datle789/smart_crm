package crm.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crm.demo.models.Roles;
import crm.demo.models.User;
import crm.demo.repo.RoleRepo;
import crm.demo.repo.UserRepo;

@Service
public class UserAdminService {
    @Autowired
    private UserRepo userRoleRepo;

    @Autowired
    private RoleRepo roleRepo;

    public List<User> getListUsers() {
        return userRoleRepo.findAll();
    }

    public User get(Long id) {
        return userRoleRepo.findById(id).get();
    }

    public List<Roles> listRoles() {
        return roleRepo.findAll();
    }

    public void save(User user) {
        userRoleRepo.save(user);
    }
}
