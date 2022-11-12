package com.yukiii.demo.entity;

import com.yukiii.demo.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "kycs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Kyc extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 3859691211467058263L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;
}
