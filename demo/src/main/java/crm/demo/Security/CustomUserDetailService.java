package crm.demo.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import crm.demo.models.User;
import crm.demo.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
  @Autowired
  private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {

      User user = userRepo.findByUserName(username);
      if (user == null) {
        throw new UsernameNotFoundException("User not found with username: " + username);
      }
      UserDetails userDetails = CustomUserDetail.mapUserToUserDetail(user);
      return userDetails;
    } catch (Exception e) {
      logger.error("Error loading user details for username: " + username, e);
      throw new UsernameNotFoundException("Error loading user details", e);
    }
  }

}
