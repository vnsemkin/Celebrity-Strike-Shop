package org.duckdns.celebritystrike.celebritystrike.dao;

import org.duckdns.celebritystrike.celebritystrike.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Integer> {
    Optional<GameEntity> findGameEntityByName(String name);
    Optional<GameEntity> findByNameIgnoreCase(String name);
}
