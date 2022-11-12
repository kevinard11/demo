package com.yukiii.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yukiii.demo.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "demo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Demo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3859691211467058263L;

    @Id
    @SequenceGenerator(name =  "seq", sequenceName = "demo_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "demo_id")
    private String demoId;

    @Column(name = "demo_name")
    private String demoName;

    @Column(name = "description")
    private String description;

    @Column(name = "level")
    private int level;

    @Column(name = "active")
    private boolean active;

    @Column(name = "version")
    private int version;

    @Column(name = "updated_by")
    private String updatedBy;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
