package tech.build.agregadorinvestimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.build.agregadorinvestimento.entity.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
