package com.gingapay.splitcoreapplicationapi.repository;

import com.gingapay.splitcoreapplicationapi.entity.SplitDistributionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SplitDistributionRepository extends JpaRepository<SplitDistributionEntity, UUID> {
}
