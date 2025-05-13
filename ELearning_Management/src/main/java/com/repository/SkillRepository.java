package com.repository;

import com.entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long>, JpaSpecificationExecutor<SkillEntity> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<SkillEntity> findByIdIn(List<Long> id);
}
