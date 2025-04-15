package org.duckdns.celebritystrike.celebritystrike.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "games")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GameEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String description;
  private String instruction;
  @OneToMany
  @JoinColumn(name = "game_id")
  private Set<Image> images;
  @OneToMany
  @JoinColumn(name = "game_id")
  private Set<GameItem> items;
}
