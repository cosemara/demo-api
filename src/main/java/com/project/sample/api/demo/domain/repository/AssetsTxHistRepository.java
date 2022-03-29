package com.project.sample.api.demo.domain.repository;

import com.project.sample.api.demo.domain.entity.AssetsTxHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AssetsTxHistRepository extends JpaRepository<AssetsTxHistory, Long> {
}
