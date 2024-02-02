package crm.demo.Payload.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import crm.demo.models.Crm;
import crm.demo.models.Roles;

public class UserResponse {
    private long id;

    private String userName;

    private String name;

    private String avatar;

    private String email;

    private String phone;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<Roles> listRoles;

    public Set<Roles> getListRoles() {
        return listRoles;
    }

    public void setListRoles(Set<Roles> listRoles) {
        this.listRoles = listRoles;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserResponse(Long id, String username, String avatar, String name, String email, String phone,
            Set<Roles> listRoles) {
        this.id = id;
        this.userName = username;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.listRoles = listRoles;
    }
}
