package crm.demo.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crm.demo.models.User;
import crm.demo.repo.UserRepo;
import crm.demo.services.UserService;

@Service
public class UserServiceImp implements UserService {
  @Autowired
  private UserRepo userRepo;

  @Override
  public User finByUserName(String userName) {
    return userRepo.findByUserName(userName);
  }

  @Override
  public boolean existByUserName(String userName) {
    return userRepo.existsByUserName(userName);
  }

  @Override
  public boolean existByEmail(String email) {
    return userRepo.existsByEmail(email);
  }

  @Override
  public User saveOrUpdate(User users) {
    return userRepo.save(users);
  }

}
