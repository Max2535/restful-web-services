package com.max.restfulwebservices.controller;

import com.max.restfulwebservices.model.User;
import com.max.restfulwebservices.exception.ResourceNotFoundException;
import com.max.restfulwebservices.service.UserDaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "User API", description = "User Management Endpoints")
public class UserController {

    @Autowired
    private UserDaoService service;

    //http://localhost:8080/api/v1/users
    @GetMapping("/users")
    @Operation(summary = "Retrieve all users", description = "Get a list of all users in the system.")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    //http://localhost:8080/api/v1/users/1
    @GetMapping("/users/{id}")
    @Operation(summary = "Retrieve a user by ID", description = "Get details of a specific user by their ID.")
    public EntityModel<User> getUserById(@PathVariable int id) {
        User user = service.getUserById(id);

        // เพิ่มลิงก์ HATEOAS
        EntityModel<User> resource = EntityModel.of(user);

        // ลิงก์ไปยังตัวเอง (self)
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getUserById(id));

        // ลิงก์ไปยังรายการผู้ใช้ทั้งหมด
        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers());

        resource.add(linkToSelf.withSelfRel());
        resource.add(linkToUsers.withRel("all-users"));

        return resource;
    }

    //http://localhost:8080/api/v1/users
    /*
    {
        "name": "Adam",
        "birthDate": "2019-01-01T00:00:00.000+0000"
    }
    */
    @PostMapping("/users")
    //@SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

       URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();

       return ResponseEntity.created(location).build();
    }

    //http://localhost:8080/api/v1/users/1
    /*
    {
        "id": 1,
        "name": "Adam",
        "birthDate": "2019-01-01T00:00:00.000+0000"
    }
    */
    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody User user, @PathVariable int id) {
        User updatedUser = service.update(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(updatedUser.getId())
            .toUri();

        return updatedUser;
    }

    //http://localhost:8080/api/v1/users/1
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);
        if (user == null) {
            throw new ResourceNotFoundException("id-" + id);
        }
    }
}
