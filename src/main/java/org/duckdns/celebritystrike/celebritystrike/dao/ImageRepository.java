package org.duckdns.celebritystrike.celebritystrike.dao;

import org.duckdns.celebritystrike.celebritystrike.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
} 