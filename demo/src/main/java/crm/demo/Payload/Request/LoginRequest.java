package crm.demo.Payload.Request;

public class LoginRequest {
  private String username;
  private String password;

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
}
