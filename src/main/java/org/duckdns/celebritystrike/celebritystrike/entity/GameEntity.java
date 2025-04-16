package org.duckdns.celebritystrike.celebritystrike.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "games")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GameEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String title;
  private String description;
  private String instruction;

  @OneToMany(
      mappedBy = "game",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<ImageEntity> images = new ArrayList<>();

  @OneToMany(
      mappedBy = "game",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<GameItemEntity> items = new ArrayList<>();
}
