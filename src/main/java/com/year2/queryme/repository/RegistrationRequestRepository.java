package com.year2.queryme.repository;

import com.year2.queryme.model.RegistrationRequest;
import com.year2.queryme.model.enums.RegistrationRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, UUID> {
    List<RegistrationRequest> findByStatusOrderByRequestedAtDesc(RegistrationRequestStatus status);
    boolean existsByEmailAndStatus(String email, RegistrationRequestStatus status);
    boolean existsByRegistrationNumberAndStatus(String registrationNumber, RegistrationRequestStatus status);
}
