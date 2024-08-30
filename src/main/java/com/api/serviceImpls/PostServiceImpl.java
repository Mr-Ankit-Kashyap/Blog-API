package com.api.serviceImpls;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.dtos.PostDto;
import com.api.dtos.PostResponse;
import com.api.entities.Category;
import com.api.entities.Post;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundException;
import com.api.repositories.CategoryRepository;
import com.api.repositories.PostRepository;
import com.api.repositories.UserRepository;
import com.api.services.PostService;



@Service
public class PostServiceImpl implements PostService 
{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	

	@Override
	public PostDto createPost(PostDto postDto , Long userId , Long categoryId) 
	{
//		search user
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","userid",userId));
		
//		search category
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
		
//		convert postDto To post
		Post post = this.modelMapper.map(postDto,Post.class);
		
//		set property in post
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
//		save post
		Post savePost = this.postRepository.save(post);
		
//		convert post To postDto
	    PostDto savePostDto = this.modelMapper.map(savePost,PostDto.class);
	    
//		return postDto
		return savePostDto;
	}

	
	
	
	
	
	
	@Override
	public PostDto updatePost(PostDto postDto, Long postId) 
	{
		
//		search post by id
		Post post = this.postRepository.findById(postId).
				orElseThrow(() -> new ResourceNotFoundException("Post","postId",postId));
		
		
//		set property in post
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		
//		save post
		Post updatePost = this.postRepository.save(post);
		
		
//		convert updatePost To updatePostDto
		PostDto updatePostDto = this.modelMapper.map(updatePost, PostDto.class);
		
//		return updatePostDto	
		return updatePostDto;
	}
	
	
	
	

	@Override
	public void deletePost(Long postId) 	
	{
//		search post
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId",postId));
		
//		delete post
		this.postRepository.delete(post);

	}

	
	
	
	
	@Override
	public PostDto getPostById(Long postId) 
	{
//		search post by id
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId",postId));
		
//		convert post To postDto
		PostDto postDto = this.modelMapper.map(post,PostDto.class);
		
//		return postDto
		return postDto;
		
	}
	
	
	
	

	@Override
	public PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir) 
	{	
		
//		sort by ascending and descending
		Sort sort= (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		
//		Request for pageNumber and pageSize
		PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,sort);
		
		
//      get post using pageRequest
		Page<Post> pagePost = this.postRepository.findAll(pageRequest);
		
		
//		get AllPost
		List<Post> allPosts = pagePost.getContent();
		
//	    convert posts To postDtos
		List<PostDto> postDtos = allPosts.stream().map((post)-> this.modelMapper.map(post,PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
//		return postResponse
		return postResponse;
	}
	
	
	
	

	@Override
	public List<PostDto> getPostByCategory(Long categoryId) 
	{
		
//		search category
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category" ,"categoryId", categoryId));
		
		
//		get AllPost by category
		List<Post> posts = this.postRepository.getByCategory(category);
		
		
//		convert posts To postDtos
		List<PostDto> postDtos = posts.stream().map((post) -> this .modelMapper.map(post,PostDto.class))
				.collect(Collectors.toList());
		
//		return postDtos
		return postDtos;
	}

	
	
	
	@Override
	public List<PostDto> getPostByUser(Long userId) 
	{
		
//		search user 
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User"  , "userId" , userId));
		
		
//		get AllPost by user
		List<Post> posts = this.postRepository.getByUser(user);
		
		
//		convert posts To postDtos	
		List<PostDto> postDtos = posts.stream().map((post) ->this. modelMapper.map(post,PostDto.class))
				.collect(Collectors.toList());
		
//		return postDtos
		return postDtos;
	}

	
	
	@Override
	public List<PostDto> searchPosts(String keyword) 
	{
		
//		search using field by keyword
		List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		
		
//		posts convert postDtos
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).
				collect(Collectors.toList());
		
//		return postDtos
		return postDtos;
	}

}