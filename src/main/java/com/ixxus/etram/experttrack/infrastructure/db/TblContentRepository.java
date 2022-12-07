package com.ixxus.etram.experttrack.infrastructure.db;

import com.ixxus.etram.experttrack.model.entity.TblContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblContentRepository extends JpaRepository<TblContentEntity, Long> {
}
