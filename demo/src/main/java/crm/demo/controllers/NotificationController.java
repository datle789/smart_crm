package crm.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crm.demo.models.Notification;
import crm.demo.repo.NotificationRepo;

@RestController
@RequestMapping(path = "/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationRepo notificationRepo;

    @GetMapping(value = "/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<Notification> getNotifications() {
        return notificationRepo.findAll();
        // return notificationRepo.findAllActiveNotifications();
    }

}
