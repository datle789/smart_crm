package crm.demo.Payload.Response;

import java.util.List;

import crm.demo.models.Crm;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String name;
  private String avatar;
  private String email;
  private String phone;
  // private List<Crm> crms;
  private List<String> listRoles;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
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

  // public List<Crm> getCrms() {
  // return crms;
  // }

  // public void setCrms(List<Crm> crms) {
  // this.crms = crms;
  // }

  public List<String> getListRoles() {
    return listRoles;
  }

  public void setListRoles(List<String> listRoles) {
    this.listRoles = listRoles;
  }

  public JwtResponse(String token, Long id, String username, String name, String avatar, String email, String phone,
      List<Crm> crms,
      List<String> listRoles) {
    this.token = token;
    this.id = id;
    this.username = username;
    this.name = name;
    this.avatar = avatar;
    this.email = email;
    this.phone = phone;
    // this.crms = crms;
    this.listRoles = listRoles;
  }

}
