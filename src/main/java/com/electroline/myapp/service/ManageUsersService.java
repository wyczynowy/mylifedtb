package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.User;

public interface ManageUsersService {
	int createNewUser(User user) throws Exception;
	User getUser(int usernameId);
	List<User> getAllUsers();
	void deleteUser(int usernameId);
	void updateUser(User user);
}
