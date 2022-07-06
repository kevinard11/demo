package com.yukiii.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "demo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Demo {

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

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
