package com.befoodly.be.repository;

import com.befoodly.be.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomerReferenceId(String customerReferenceId);
    Optional<Address> findByCustomerReferenceIdAndTitle(String customerReferenceId, String title);
    void deleteByCustomerReferenceIdAndTitle (String customerReferenceId, String title);
}
