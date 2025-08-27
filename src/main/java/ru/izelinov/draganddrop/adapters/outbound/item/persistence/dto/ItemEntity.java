package ru.izelinov.draganddrop.adapters.outbound.item.persistence.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
public class ItemEntity {

  @Id
  private UUID id;
  @Version
  private Long version;
  @Column(nullable = false, updatable = false)
  private String name;
  @Column(nullable = false)
  private Long position;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getPosition() {
    return position;
  }

  public void setPosition(Long position) {
    this.position = position;
  }
}
