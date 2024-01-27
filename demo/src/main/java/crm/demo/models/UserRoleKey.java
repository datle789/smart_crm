package crm.demo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserRoleKey implements Serializable {
    @Column(name = "user_id")
    Long userId;

    @Column(name = "role_id")
    Long roleId;
}
