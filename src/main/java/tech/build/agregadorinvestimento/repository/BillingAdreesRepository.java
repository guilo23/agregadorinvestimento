package tech.build.agregadorinvestimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.build.agregadorinvestimento.entity.BillingAdrees;

import java.util.UUID;

public interface BillingAdreesRepository extends JpaRepository<BillingAdrees, UUID> {
}
