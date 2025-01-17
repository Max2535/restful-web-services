package com.max.restfulwebservices.repository;

import com.max.restfulwebservices.dto.UserDTO;
import com.max.restfulwebservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new com.max.restfulwebservices.dto.UserDTO(u.id, u.name, u.email) FROM User u")
    List<UserDTO> findAllUsers();
}