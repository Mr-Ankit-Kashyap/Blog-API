package com.api.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.configs.AppConstants;
import com.api.dtos.ApiResponse;
import com.api.dtos.PostDto;
import com.api.dtos.PostResponse;
import com.api.services.FileService;
import com.api.services.PostService;

import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto ,@PathVariable Long userId  ,@PathVariable Long categoryId)
	{
		
		PostDto savePostDto = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<>(savePostDto , HttpStatus.CREATED);
		
	}
	
	
	
	
	
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable Long postId)
	{
		PostDto updatePostDto = this.postService.updatePost(postDto,postId);
		
		return new ResponseEntity<>(updatePostDto , HttpStatus.OK); 
	}
	
	
	
	
	
	
	
	@DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId)
    {
    	this.postService.deletePost(postId);
    	
    	return new ResponseEntity<ApiResponse>( new ApiResponse("Post delete Successfully" , true) ,HttpStatus.OK);
    }
	
	
	
	
	
	
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getpost(@PathVariable Long postId)
	{
		
		PostDto postDto = this.postService.getPostById(postId);
		
		return new ResponseEntity<>(postDto , HttpStatus.OK); 
		
	}
	
	
	
	
	
	
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse>  getPosts(
			@RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER ,required = false) Integer pageNumber ,
			@RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize ,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY , required = false) String sortBy ,
			@RequestParam(value = "sortDir" , defaultValue =AppConstants.SORT_DIR , required =false) String sortDir )
			
	{
		
	   PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse , HttpStatus.OK); 
		
	}
	
	
	
	
	
	
	
	@GetMapping("/posts/{categoryId}")
	public ResponseEntity<List<PostDto>>  getPostByCategory(@PathVariable Long categoryId)
	{
		List<PostDto> postDtos = this.postService.getPostByCategory(categoryId);
		
		return new ResponseEntity<>(postDtos , HttpStatus.OK); 
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		
		List<PostDto> searchPostDtos = this.postService.searchPosts(keyword);
		
		ResponseEntity<List<PostDto>> responseEntity = new ResponseEntity<>(searchPostDtos,HttpStatus.OK);
		 
		return responseEntity; 
		
	}
	
	
	
	
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image , @PathVariable Long postId) throws Exception
	{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePost,HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	@GetMapping(value = "post/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName ,HttpServletResponse response) throws IOException
	{
		
		InputStream resource = this.fileService.getResource(path,imageName);
	    ((ServletResponse) resource).setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}

	
	
	
	
	
	
	
}

