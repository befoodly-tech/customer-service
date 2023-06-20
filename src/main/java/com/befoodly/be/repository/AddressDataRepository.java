package com.befoodly.be.repository;

import com.befoodly.be.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDataRepository extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findByCustomerReferenceId(String customerReferenceId);
}
