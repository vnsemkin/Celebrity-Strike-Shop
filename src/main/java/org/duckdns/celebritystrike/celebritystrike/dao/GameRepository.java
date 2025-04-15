package org.duckdns.celebritystrike.celebritystrike.dao;

import org.duckdns.celebritystrike.celebritystrike.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {}
