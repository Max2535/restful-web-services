package com.max.restfulwebservices.repository;

import com.max.restfulwebservices.dto.PostDTO;
import com.max.restfulwebservices.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // ดึงข้อมูลโพสต์ทั้งหมดด้วย PostDTO เพื่อลดข้อมูลที่ไม่จำเป็น
    @Query("SELECT new com.max.restfulwebservices.dto.PostDTO(p.id, p.title, p.content) FROM Post p")
    List<PostDTO> findAllPosts();

    // ดึงโพสต์ทั้งหมดของผู้ใช้เฉพาะ ID
    @Query("SELECT new com.max.restfulwebservices.dto.PostDTO(p.id, p.title, p.content) FROM Post p WHERE p.user.id = :userId")
    List<PostDTO> findPostsByUserId(@Param("userId") Long userId);
}
