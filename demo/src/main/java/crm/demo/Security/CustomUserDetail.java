package crm.demo.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import crm.demo.models.Crm;
import crm.demo.models.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CustomUserDetail implements UserDetails {
  private static final Logger logger = LoggerFactory.getLogger(CustomUserDetail.class);

  private long id;
  private String username;
  @JsonIgnore
  private String password;
  private String email;
  private String name;
  private String avatar;
  private String phone;
  private int status;
  private List<Crm> crms;
  private Collection<? extends GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Transactional
  public static CustomUserDetail mapUserToUserDetail(User users) {
    List<GrantedAuthority> lAuthorities = new ArrayList<>(users.getListRoles()).stream()
        .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
        .collect(Collectors.toList());

    CustomUserDetail customUserDetail = new CustomUserDetail(users.getId(),
        users.getUserName(),
        users.getPassword(),
        users.getEmail(),
        users.getName(),
        users.getAvatar(),
        users.getPhone(),
        users.getStatus(),
        users.getCrms(), lAuthorities);
    return customUserDetail;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
