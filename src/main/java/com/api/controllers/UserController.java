package com.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dtos.ApiResponse;
import com.api.dtos.UserDto;
import com.api.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		
		UserDto createUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/{userId}")
	public  ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Long userId)
	{

       UserDto updateUserDto = this.userService.updateUser(userDto , userId);
       
      return new ResponseEntity<>(updateUserDto,HttpStatus.OK);
      
		
	}
	
	
	//@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
    	
    	this.userService.deleteUser(userId);
    	
    	return new ResponseEntity<>( new ApiResponse("User deleted Succesfully",true),HttpStatus.OK);
    	
    }
	
	
	
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable Long userId){
		
		UserDto userDto = this.userService.getUserById(userId);
		
		return new ResponseEntity<>(userDto,HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getUser(){
		
		List<UserDto> allUserDto = this.userService.getAllUser();
		
		return new ResponseEntity<>(allUserDto,HttpStatus.OK);
	}

}
