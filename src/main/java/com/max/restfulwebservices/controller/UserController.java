package com.max.restfulwebservices.controller;

import com.max.restfulwebservices.dao.User;
import com.max.restfulwebservices.service.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return service.findOne(id);
    }

    //http://localhost:8080/api/v1/users
    /*
    {
        "name": "Adam",
        "birthDate": "2019-01-01T00:00:00.000+0000"
    }
    */
    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);
    }
}
