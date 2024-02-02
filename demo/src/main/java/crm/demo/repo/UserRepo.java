package crm.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crm.demo.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  User findByUserName(String userName);

  boolean existsByUserName(String userName);

  boolean existsByEmail(String email);

}
