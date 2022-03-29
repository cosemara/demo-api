package com.project.sample.api.demo.domain.repository;

import com.project.sample.api.demo.domain.entity.Assets;
import com.project.sample.api.demo.domain.type.AssetsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AssetsRepository extends JpaRepository<Assets, Long> {
    List<Assets> findByUserId(Long userId);

    Optional<Assets> findByUserIdAndAssetsType(Long userId, AssetsType assetType);
}
