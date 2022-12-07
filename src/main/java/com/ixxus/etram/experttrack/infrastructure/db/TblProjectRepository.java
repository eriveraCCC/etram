/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.infrastructure.db;

import com.ixxus.etram.experttrack.model.entity.TblProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblProjectRepository extends JpaRepository<TblProjectEntity, Long> {
}
