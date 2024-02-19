package crm.demo.Payload.Request;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupRequest {
  private String username;
  private String password;
  private String email;
  private String name;
  private String phone;
  private LocalDateTime createdAt;
  private int status;
  private Set<String> listRoles;

  private static final String PHONE_NUMBER_PATTERN = "^\\+?[0-9]{1,3}[\\s-]?\\(?[0-9]{1,3}\\)?[\\s-]?[0-9]{3,}$";

  public String getUsername() {
    if (username == null || username.trim().isEmpty()) {
      username = null;
    }
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    if (password == null || password.trim().isEmpty()) {
      password = null;
    }
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
    // Kiểm tra xem phoneNumber có phải là null không
    if (phone != null) {
      if (isValidPhoneNumber(phone)) {
        this.phone = phone;
      } else {
        this.phone = null;
      }
    } else {
      this.phone = null;
    }
  }

  // Kiểm tra định dạng số điện thoại bằng regex
  private boolean isValidPhoneNumber(String phoneNumber) {
    Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
    Matcher matcher = pattern.matcher(phoneNumber);
    return matcher.matches();
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
