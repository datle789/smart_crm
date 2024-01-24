package crm.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Roles")
@Data
public class Roles {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "RoleID")
  private int roleId;
  @Column(name = "RoleName")
  @Enumerated(EnumType.STRING)
  private Erole roleName;
}
