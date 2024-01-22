package crm.demo.repo;

import crm.demo.models.Image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepo extends JpaRepository<Image, Long> {

    Optional<Image> findByName(String fileName);
}
