package com.example.ecommerce.repository;

import com.example.ecommerce.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    UserDetails findByUserName(@Param("user_name") String user_name);
    UserDetails findByUserNameAndPassword(@Param("user_name") String user_name, String password);
}
