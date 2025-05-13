package com.repository;

import com.entity.CompanyEntity;
import com.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdIsNot(String email, Long userId);

    UserEntity findByRefreshTokenAndEmail(String refreshToken, String email);

    List<UserEntity> findByCompany(CompanyEntity company);
}
