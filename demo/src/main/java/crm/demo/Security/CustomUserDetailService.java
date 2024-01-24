package crm.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import crm.demo.models.User;
import crm.demo.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
  @Autowired
  private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUserName(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    return CustomUserDetail.mapUserToUserDetail(user);
  }

}
