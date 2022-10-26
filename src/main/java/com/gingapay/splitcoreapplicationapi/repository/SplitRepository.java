package com.gingapay.splitcoreapplicationapi.repository;

import com.gingapay.splitcoreapplicationapi.entity.SplitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SplitRepository extends JpaRepository<SplitEntity, UUID> {

    Optional<SplitEntity> findByOrderId(String orderId);

}
