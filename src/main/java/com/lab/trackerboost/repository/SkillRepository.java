package com.lab.trackerboost.repository;

import com.lab.trackerboost.model.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long> {
    Optional<SkillEntity> findSkillByName(String name);
}
