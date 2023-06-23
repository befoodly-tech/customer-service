package com.befoodly.be.repository;

import com.befoodly.be.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressDataRepository extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findByCustomerReferenceId(String customerReferenceId);

    Optional<AddressEntity> findByCustomerReferenceIdAndTitle(String customerReferenceId, String title);

    void deleteByCustomerReferenceIdAndTitle (String customerReferenceId, String title);
}
