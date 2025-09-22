package com.backend.blog.services;

import java.util.List;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;

public interface PostService {

	
	//create
	PostDto createPost(PostDto postDto, Integer userId,Integer categoryId);
	
	//update 
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all posts
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	// get single post
	PostDto getPostById(Integer postId);
	
	// get All posts by category
	PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize);
	
	// get All posts by user
	PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize);
	
	// search posts
	List<PostDto> searchPosts(String keyword);
	
	
}
