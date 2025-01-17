package com.max.restfulwebservices.controller;

import com.max.restfulwebservices.dto.PostDTO;
import com.max.restfulwebservices.exception.ResourceNotFoundException;
import com.max.restfulwebservices.model.User;
import com.max.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    //http://localhost:8080/api/v1/users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //http://localhost:8080/api/v1/users

    /*
    {
        "name": "John Doe",
        "email": "john.doe@example.com"
    }
    */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user); // บันทึกผู้ใช้ลงฐานข้อมูล
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // ส่งกลับข้อมูลพร้อม HTTP 201
    }


    //http://localhost:8080/api/v1/users/1
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    //http://localhost:8080/api/v1/users/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) { // ตรวจสอบว่าผู้ใช้มีอยู่หรือไม่
            userRepository.deleteById(id); // ลบผู้ใช้
            return ResponseEntity.noContent().build(); // ส่ง HTTP 204
        }
        return ResponseEntity.notFound().build(); // ส่ง HTTP 404 ถ้าผู้ใช้ไม่มีอยู่
    }

    //http://localhost:8080/api/v1/users/1/posts
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> getUserPosts(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    List<PostDTO> posts = user.getPosts().stream()
                            .map(post -> new PostDTO(post.getId(), post.getTitle(), post.getContent()))
                            .toList();
                    return ResponseEntity.ok(posts);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}




//
//import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
//import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import com.max.restfulwebservices.dao.User;
//import com.max.restfulwebservices.exception.ResourceNotFoundException;
//import com.max.restfulwebservices.service.UserDaoService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.json.MappingJacksonValue;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//import java.util.List;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@RestController
//@RequestMapping("/api/v1")
//@Tag(name = "User API", description = "User Management Endpoints")
//public class UserController {
//
//    @Autowired
//    private UserDaoService service;
//
//    //http://localhost:8080/api/v1/users
//    @GetMapping("/users")
//    @Operation(summary = "Retrieve all users", description = "Get a list of all users in the system.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
//            @ApiResponse(responseCode = "500", description = "Internal Server Error")
//    })
//    public List<User> getAllUsers() {
//        return service.findAll();
//    }
//
//    //http://localhost:8080/api/v1/users/1
//    @GetMapping("/users/{id}")
//    @Operation(summary = "Retrieve a user by ID", description = "Get details of a specific user by their ID.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = User.class),
//                            examples = @ExampleObject(
//                                    name = "Example User",
//                                    summary = "An example of a user",
//                                    value = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"
//                            )))
//    })
//    public EntityModel<User> getUserById(@PathVariable int id) {
//        User user = service.getUserById(id);
//
//        // เพิ่มลิงก์ HATEOAS
//        EntityModel<User> resource = EntityModel.of(user);
//
//        // ลิงก์ไปยังตัวเอง (self)
//        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getUserById(id));
//
//        // ลิงก์ไปยังรายการผู้ใช้ทั้งหมด
//        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers());
//
//        resource.add(linkToSelf.withSelfRel());
//        resource.add(linkToUsers.withRel("all-users"));
//
//        return resource;
//    }
//
//    //http://localhost:8080/api/v1/users
//    /*
//    {
//        "name": "Adam",
//        "birthDate": "2019-01-01T00:00:00.000+0000"
//    }
//    */
//    @PostMapping("/users")
//    //@SecurityRequirement(name = "bearerAuth")
//    @Operation(summary = "Create a new user", description = "Add a new user to the system.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "User created successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid input")
//    })
//    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
//        User savedUser = service.save(user);
//
//       URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//            .path("/{id}")
//            .buildAndExpand(savedUser.getId())
//            .toUri();
//
//       return ResponseEntity.created(location).build();
//    }
//
//    //http://localhost:8080/api/v1/users/1
//    /*
//    {
//        "id": 1,
//        "name": "Adam",
//        "birthDate": "2019-01-01T00:00:00.000+0000"
//    }
//    */
//    @PutMapping("/users/{id}")
//    public User updateUser(@Valid @RequestBody User user, @PathVariable int id) {
//        User updatedUser = service.update(user);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//            .path("/{id}")
//            .buildAndExpand(updatedUser.getId())
//            .toUri();
//
//        return updatedUser;
//    }
//
//    //http://localhost:8080/api/v1/users/1
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable int id) {
//        User user = service.deleteById(id);
//        if (user == null) {
//            throw new ResourceNotFoundException("id-" + id);
//        }
//    }
//
//    //http://localhost:8080/api/v1/users/filter
//    @GetMapping("/users/filter")
//    public MappingJacksonValue getUsers() {
//        List<User> users = service.findAll();
//        // กำหนดฟิลด์ที่ต้องการแสดง
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
//        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
//
//        // ใส่ข้อมูลและ Filter ใน Response
//        MappingJacksonValue mapping = new MappingJacksonValue(users);
//        mapping.setFilters(filters);
//
//        return mapping;
//    }
//
//    //http://localhost:8080/api/v1/users/dynamic?fields=id&fields=name
//    @GetMapping("/users/dynamic")
//    public MappingJacksonValue getUsersWithDynamicFields(@RequestParam List<String> fields) {
//        List<User> users = service.findAll();
//        // ใช้ฟิลด์ที่รับมาจาก Query Parameter
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields.toArray(new String[0]));
//        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter);
//
//        MappingJacksonValue mapping = new MappingJacksonValue(users);
//        mapping.setFilters(filters);
//
//        return mapping;
//    }
//}
