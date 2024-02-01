package crm.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crm.demo.models.Attachment;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
}
