package com.smartCaa.smartCaa.repositories;

import com.smartCaa.smartCaa.models.KeyAssumptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface KeyAssumptionsRepository extends JpaRepository<KeyAssumptions, Long> {
    Optional<KeyAssumptions> findByKeyIgnoreCase(String key);

    List<KeyAssumptions> findByKeyContains(String key);

    List<KeyAssumptions> findByKeyIn(Collection<String> keys);
}
