package com.backend.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.blog.config.AppConstants;
import com.backend.blog.entities.Category;
import com.backend.blog.entities.Post;
import com.backend.blog.entities.User;
import com.backend.blog.exceptions.ResourceNotFoundException;
import com.backend.blog.payloads.PostDto;
import com.backend.blog.payloads.PostResponse;
import com.backend.blog.repositories.CategoryRepo;
import com.backend.blog.repositories.PostRepo;
import com.backend.blog.repositories.UserRepo;
import com.backend.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = this.userRepo.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("User", "userId ", userId));
		
		Category category = this.categoryRepo.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId ", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("defaultImg.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
		.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","postId", postId));
        this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber-1, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
		              .collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber()+1);
		postResponse.setPageSize(pagePost.getNumberOfElements());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
		             .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId ", categoryId));
		
		Pageable p = PageRequest.of(pageNumber-1, pageSize);
		
		Page<Post> postByCategory = this.postRepo.findByCategory(category,p);
		
		List<Post> posts = postByCategory.getContent();
		
		if(posts.isEmpty()){
			throw new ResourceNotFoundException("Post", "categoryId", categoryId);
		}
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
		              .collect(Collectors.toList());
		
        PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(postByCategory.getNumber()+1);
		postResponse.setPageSize(postByCategory.getNumberOfElements());
		postResponse.setTotalElements(postByCategory.getTotalElements());
		postResponse.setTotalPages(postByCategory.getTotalPages());
		postResponse.setLastPage(postByCategory.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId ", userId));
		
		Pageable p = PageRequest.of(pageNumber-AppConstants.ONE, pageSize);
		
		Page<Post> postByUser = this.postRepo.findByUser(user,p);
		
		List<Post> posts = postByUser.getContent();
		
		if(posts.isEmpty()) {
			throw new ResourceNotFoundException("Post", "userId", userId);
		}
		
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
		              .collect(Collectors.toList());
		
		  PostResponse postResponse = new PostResponse();
			
		  postResponse.setContent(postDtos);
		  postResponse.setPageNumber(postByUser.getNumber()+AppConstants.ONE);
		  postResponse.setPageSize(postByUser.getNumberOfElements());
		  postResponse.setTotalElements(postByUser.getTotalElements());
		  postResponse.setTotalPages(postByUser.getTotalPages());
		  postResponse.setLastPage(postByUser.isLast());
			
			return postResponse;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream()
		.map(post -> this.modelMapper.map(post, PostDto.class))
		.toList();
		return postDtos;
	}

}
