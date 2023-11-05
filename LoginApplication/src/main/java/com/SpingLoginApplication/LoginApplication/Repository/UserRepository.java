package com.SpingLoginApplication.LoginApplication.Repository;

import com.SpingLoginApplication.LoginApplication.Model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity ,Long> {
UserEntity findByEmail(String email);
@Modifying
    @Query("UPDATE UserEntity u SET u.roll= :newRole WHERE u.id= :userId")
    int updateUserRole(Long userId,String newRole);
    boolean existsByEmail(String email);

@Query("SELECT u FROM UserEntity u WHERE u.name LIKE ?1%")
Page<UserEntity> findUsersByNameStartingWith(String startingLetter, Pageable pageable);
}
