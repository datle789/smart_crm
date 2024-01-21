package crm.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crm.demo.models.Crm;
import crm.demo.models.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
    @Query("SELECT c FROM Notification c WHERE c.status = 1")
    List<Notification> findAllActiveNotifications();
}
