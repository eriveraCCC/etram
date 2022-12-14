/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.infrastructure.db.repository;

import com.ixxus.etram.experttrack.model.entity.TblDmsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblDmsRepository extends JpaRepository<TblDmsEntity, Long> {
}
