package com.api.services;

import java.util.List;

import com.api.dtos.PostDto;
import com.api.dtos.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto , Long userId , Long categoryId);
	
	PostDto updatePost(PostDto postDto , Long postId);
	
	void deletePost(Long postId);
	
	PostDto getPostById(Long postId);
	
	PostResponse getAllPost(Integer pageNumber ,Integer pageSize,String sortBy,String sortDir);
	
	List<PostDto> getPostByCategory(Long categoryId);
	
	List<PostDto> getPostByUser(Long userId);
	
	List<PostDto> searchPosts(String keyword);	

}
