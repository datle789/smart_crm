package crm.demo.Payload.Request;

import java.time.LocalDateTime;
import java.util.Set;

public class SignupRequest {
  private String username;
  private String password;
  private String email;
  private String name;
  private String phone;
  private LocalDateTime createdAt;
  private int status;
  private Set<String> listRoles;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Set<String> getListRoles() {
    return listRoles;
  }

  public void setListRoles(Set<String> listRoles) {
    this.listRoles = listRoles;
  }
}
