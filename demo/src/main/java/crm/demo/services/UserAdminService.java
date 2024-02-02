package crm.demo.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crm.demo.controllers.CrmController;
import crm.demo.models.Roles;
import crm.demo.models.User;
import crm.demo.repo.RoleRepo;
import crm.demo.repo.UserRepo;

@Service
public class UserAdminService {
    @Autowired
    private UserRepo userRoleRepo;

    private static final Logger logger = LoggerFactory.getLogger(CrmController.class);

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
