/*
 * (c) 2022 Copyright Clearance Center
 */
package com.ixxus.etram.experttrack.infrastructure.db.repository;

import com.ixxus.etram.experttrack.model.entity.TblPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblPageRepository extends JpaRepository<TblPageEntity, Long> {
}
