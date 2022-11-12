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
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 3859691211467058263L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "kyc_id")
  private Kyc kyc;

  @JsonManagedReference
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Demo> demo;

  @ManyToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Role> role;
}
