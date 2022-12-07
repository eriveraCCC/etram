package com.ixxus.etram.experttrack.infrastructure.db;

import com.ixxus.etram.experttrack.model.entity.TblDmsEntity;
import com.ixxus.etram.experttrack.model.entity.TblDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblDocumentsRepository extends JpaRepository<TblDocumentEntity, Long> {
}
