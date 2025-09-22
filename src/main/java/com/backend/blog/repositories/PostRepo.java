package com.backend.blog.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.blog.entities.Category;
import com.backend.blog.entities.Post;
import com.backend.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

	Page<Post> findByUser(User user,Pageable p);
	Page<Post> findByCategory(Category category, Pageable p);
	
	List<Post> findByTitleContaining(String keyword);
}
