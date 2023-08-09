package com.befoodly.be.repository;

import com.befoodly.be.entity.CookDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookDataRepository extends JpaRepository<CookDataEntity, Long> {
}
