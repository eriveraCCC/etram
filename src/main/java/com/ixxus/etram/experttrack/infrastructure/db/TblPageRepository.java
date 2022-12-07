package com.ixxus.etram.experttrack.infrastructure.db;

import com.ixxus.etram.experttrack.model.entity.TblDocumentEntity;
import com.ixxus.etram.experttrack.model.entity.TblPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblPageRepository extends JpaRepository<TblPageEntity, Long> {
}
