package com.befoodly.be.repository;

import com.befoodly.be.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorDataRepository extends JpaRepository<VendorEntity, Long> {
    Optional<VendorEntity> findById(Long id);
}
