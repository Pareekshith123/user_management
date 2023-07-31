package com.userbackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userbackend.entity.UserEntity;
@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long>{
}
