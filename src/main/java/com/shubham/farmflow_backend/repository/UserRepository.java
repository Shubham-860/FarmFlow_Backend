package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findUserById(Long id);
}
