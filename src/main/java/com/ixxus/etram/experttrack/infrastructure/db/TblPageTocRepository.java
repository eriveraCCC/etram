package com.ixxus.etram.experttrack.infrastructure.db;

import com.ixxus.etram.experttrack.model.entity.TblPageEntity;
import com.ixxus.etram.experttrack.model.entity.TblPageTocEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblPageTocRepository extends JpaRepository<TblPageTocEntity, Long> {
}
