package com.yukiii.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yukiii.demo.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 3859691211467058263L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "role")
  private String role;

  @Column(name = "description")
  private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "users_roles",
    joinColumns = {
    @JoinColumn(name = "role_id", referencedColumnName = "id")
  }, inverseJoinColumns = {
    @JoinColumn(name = "user_id", referencedColumnName = "id")
  })
  private List<User> user;
}
