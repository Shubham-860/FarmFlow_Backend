package com.shubham.farmflow_backend.repository;

import com.shubham.farmflow_backend.entity.Enums;
import com.shubham.farmflow_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findUserByEmail(String email);

    User findUserById(Long id);

    Long countUserByRole(Enums.Role role);

    List<User> findUsersByRole(Enums.Role role);

    @Query("SELECT u FROM User u")
    List<User> getAllUsers();

}
