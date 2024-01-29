package crm.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crm.demo.models.Notification;
import crm.demo.models.UserRole;
import crm.demo.repo.UserRoleRepo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class TestController {
  private static final Logger logger = LoggerFactory.getLogger(TestController.class);

  @Autowired
  private UserRoleRepo userRoleRepo;

  @GetMapping("/all")
  public String allAccess() {
    return "Public";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER')")
  public String userAccess() {
    return "User";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin";
  }

  @GetMapping("/userRole")
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserRole> getUserRole() {
    return userRoleRepo.findAll();
    // return notificationRepo.findAllActiveNotifications();
  }
}
