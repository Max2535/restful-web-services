package com.max.restfulwebservices.controller;

import com.max.restfulwebservices.dto.PostDTO;
import com.max.restfulwebservices.model.Post;
import com.max.restfulwebservices.model.User;
import com.max.restfulwebservices.repository.PostRepository;
import com.max.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

   //http://localhost:8080/api/v1/posts
    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postRepository.findAllPosts();
    }

    //http://localhost:8080/api/v1/posts/users/1/posts
    @GetMapping("/users/{id}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Long id) {
        List<PostDTO> posts = postRepository.findPostsByUserId(id);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posts);
    }

    //http://localhost:8080/api/v1/posts/1/posts
    /*
    {
        "title": "Post Title",
        "content": "Post Content"
    }
    */
    @PostMapping("/{userId}/posts")
    public ResponseEntity<Object> createPost(@PathVariable Long userId, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            post.setUser(user);
            postRepository.save(post);
            return ResponseEntity.ok(postRepository.findPostsByUserId(userId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
