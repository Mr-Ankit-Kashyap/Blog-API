package com.api.serviceImpls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.dtos.UserDto;
import com.api.entities.User;
import com.api.exceptions.ResourceNotFoundException;
import com.api.repositories.UserRepository;
import com.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	
	@Override
	public UserDto registerNewUser(UserDto userdto) {
		User user = this.modelMapper.map(userdto,User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User registerUser = this.userRepository.save(user);
		UserDto registerUserdto = this.modelMapper.map(registerUser,UserDto.class);
		return registerUserdto;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "userId" ,userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAbout(userDto.getAbout());
		User updateUser = this.userRepository.save(user);
		UserDto updateUserDto = this.modelMapper.map(updateUser,UserDto.class);
		return updateUserDto;
	}

	@Override
	public void deleteUser(Long userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "userId" ,userId));
		this.userRepository.delete(user);	
	}

	@Override
	public UserDto getUserById(Long userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "userId" ,userId));
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.userRepository.findAll();
		List<UserDto> userDtos = users.stream().map((user)-> this.modelMapper.map(user,UserDto.class)).collect(Collectors.toList());	
		return userDtos;
	}

}
