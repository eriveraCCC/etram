package com.ixxus.etram.experttrack.infrastructure.db;

import com.ixxus.etram.experttrack.model.entity.TblContentEntity;
import com.ixxus.etram.experttrack.model.entity.TblDmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblDmsRepository extends JpaRepository<TblDmsEntity, Long> {
}
