package crm.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.demo.models.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

}
