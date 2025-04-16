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
@ToString(exclude = {"imageEntities", "items"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GameEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String title;
  private String description;
  private String instruction;
  
  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ImageEntity> imageEntities = new ArrayList<>();
  
  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<GameItemEntity> items = new ArrayList<>();
  
  public void addImage(ImageEntity image) {
    imageEntities.add(image);
    image.setGame(this);
  }
  
  public void removeImage(ImageEntity image) {
    imageEntities.remove(image);
    image.setGame(null);
  }
  
  public void addItem(GameItemEntity item) {
    items.add(item);
    item.setGame(this);
  }
  
  public void removeItem(GameItemEntity item) {
    items.remove(item);
    item.setGame(null);
  }
}
