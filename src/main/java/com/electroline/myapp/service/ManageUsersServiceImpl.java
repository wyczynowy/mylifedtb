package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.electroline.myapp.dao.ManageUsersDao;
import com.electroline.myapp.domain.User;

@Service(value = "manageUsersService")
public class ManageUsersServiceImpl implements ManageUsersService{

	@Autowired
	@Qualifier(value = "manageUsersDao")
	ManageUsersDao manageUsersDao;
	
	@Override
	public int createNewUser(User user) throws Exception {
		return manageUsersDao.createNewUser(user);
	}

	@Override
	public User getUser(int usernameId) {
		return manageUsersDao.getUser(usernameId);
	}

	@Override
	public List<User> getAllUsers() {
		return manageUsersDao.getAllUsers();
	}

	@Override
	public void deleteUser(int usernameId) {
		manageUsersDao.deleteUser(usernameId);		
	}

	@Override
	public void updateUser(User user) {
		manageUsersDao.updateUser(user);		
	}

}
