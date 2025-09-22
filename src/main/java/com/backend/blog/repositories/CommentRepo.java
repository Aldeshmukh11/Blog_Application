package com.backend.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.blog.entities.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer>{
	
	

}
