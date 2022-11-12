package com.yukiii.demo.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.yukiii.demo.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EventStore extends BaseEntity {

  @Id
  @Column(name = "key")
  private UUID key;

  @Column(name = "exchange", nullable = false)
  private String exchange;

  @Column(name = "routing_key", nullable = false)
  private String routingKey;

  @Column(columnDefinition = "jsonb")
  @Type(type = "jsonb")
  @JsonRawValue
  private String message;

  @Column(name = "published_at")
  private LocalDateTime publishedAt;
}
