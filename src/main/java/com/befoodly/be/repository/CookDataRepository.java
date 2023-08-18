package com.befoodly.be.repository;

import com.befoodly.be.entity.CookDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookDataRepository extends JpaRepository<CookDataEntity, Long> {

    @Query(nativeQuery = true, value = "select * from cook_data where order_counts>=:orderCounts and status!='REMOVED' limit 5")
    List<CookDataEntity> findPopularCooks (Long orderCounts);

    List<CookDataEntity> findByVendorId (Long vendorId);
}
