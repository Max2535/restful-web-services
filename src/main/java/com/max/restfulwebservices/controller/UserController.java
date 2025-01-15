package com.max.restfulwebservices.controller;

import com.max.restfulwebservices.dao.User;
import com.max.restfulwebservices.exception.UserNotFoundException;
import com.max.restfulwebservices.service.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserDaoService service;

    //http://localhost:8080/api/v1/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    //http://localhost:8080/api/v1/users/1
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.getUserById(id);
        return user;
    }

    //http://localhost:8080/api/v1/users
    /*
    {
        "name": "Adam",
        "birthDate": "2019-01-01T00:00:00.000+0000"
    }
    */
    @PostMapping("/users")
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
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, @PathVariable int id) {
        User updatedUser = service.update(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(updatedUser.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    //http://localhost:8080/api/v1/users/1
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException("id-" + id);
        }
    }
}
