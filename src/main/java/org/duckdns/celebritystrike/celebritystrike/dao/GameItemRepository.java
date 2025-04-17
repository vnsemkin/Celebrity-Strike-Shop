package org.duckdns.celebritystrike.celebritystrike.dao;

import org.duckdns.celebritystrike.celebritystrike.entity.GameItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameItemRepository extends JpaRepository<GameItemEntity, Long> {
} 