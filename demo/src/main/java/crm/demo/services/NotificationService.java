package crm.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import crm.demo.models.Crm;
import crm.demo.models.Notification;
import crm.demo.repo.NotificationRepo;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepo notificationRepo;

    public void createNotification(Crm crm) {
        Notification notification = new Notification();
        notification.setTitle(crm.getTitle());
        notification.setContent(crm.getDescription());
        notification.setCrm(crm);
        notificationRepo.save(notification);
    }

}
