package com.api.services;

import java.util.List;

import com.api.dtos.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto userdto);

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, Long userId);

	void deleteUser(Long userId);

	UserDto getUserById(Long userId);

	List<UserDto> getAllUser();

}
