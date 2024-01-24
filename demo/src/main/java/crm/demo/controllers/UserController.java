package crm.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crm.demo.models.Crm;
import crm.demo.models.User;
import crm.demo.repo.UserRepo;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/")
    public List<User> getCrm() {
        // return crmRepo.findAll();
        return userRepo.findAll();
    }
}
