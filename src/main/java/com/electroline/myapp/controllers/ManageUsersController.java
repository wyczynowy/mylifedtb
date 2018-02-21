package com.electroline.myapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.electroline.myapp.domain.Authority;
import com.electroline.myapp.domain.User;
import com.electroline.myapp.service.ManageAuthoritiesService;
import com.electroline.myapp.service.ManageUsersService;

@Controller
@RequestMapping(value = "/additional")
public class ManageUsersController {

	@Autowired
	@Qualifier(value = "manageUsersService")
	ManageUsersService manageUsersService;
	
	@Autowired
	@Qualifier(value = "manageAuthoritiesService")
	ManageAuthoritiesService manageAuthoritiesService;
	
	@RequestMapping(value="/")
	public String additional(Model model) {
		List<User> uList = new ArrayList<User>();
		uList = manageUsersService.getAllUsers();
		model.addAttribute("userList", uList);
		
		List<Authority> aList = new ArrayList<Authority>();
		aList = manageAuthoritiesService.getAllAuthorities();
		model.addAttribute("authorityList", aList);
		
		return "additional";
	}
	
	@RequestMapping(value = "/adduser")
	public String addUser(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newAuthority", new Authority());
		return "adduser";
	}
	
	@RequestMapping(value = "/addfilleduserform")
	public String addFilledUserForm(Model model, @RequestParam Map<String, String> params) {
		
		User user = new User();
		user.setUsername(params.get("username"));
		user.setPassword(new BCryptPasswordEncoder().encode(params.get("password")));
		user.setEnabled(Integer.parseInt(params.get("enabled")));

		Authority authority = new Authority();
		authority.setUsername(params.get("username"));
		authority.setAuthority(params.get("authority"));
		
		try {
			@SuppressWarnings("unused")
			int newUserId = manageUsersService.createNewUser(user);
			@SuppressWarnings("unused")
			int newAuthorityId = manageAuthoritiesService.createAuthority(authority);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<User> uList = new ArrayList<User>();
		uList = manageUsersService.getAllUsers();
		model.addAttribute("userList", uList);
		
		List<Authority> aList = new ArrayList<Authority>();
		aList = manageAuthoritiesService.getAllAuthorities();
		model.addAttribute("authorityList", aList);
		
		return "additional";
	}
	
	@RequestMapping(value = "/deleteuser")
	public String deleteUser(Model model, @RequestParam("userId") int userId) {
		
		manageAuthoritiesService.deleteAuthority(userId);
		manageUsersService.deleteUser(userId);
		
		List<User> uList = new ArrayList<User>();
		uList = manageUsersService.getAllUsers();
		model.addAttribute("userList", uList);
		
		List<Authority> aList = new ArrayList<Authority>();
		aList = manageAuthoritiesService.getAllAuthorities();
		model.addAttribute("authorityList", aList);
		
		return "additional";
	}
	
	@RequestMapping(value = "/edituser")
	public String editUser(Model model, @RequestParam("userId") int userId) {
		
		User user = new User();
		user = manageUsersService.getUser(userId);
		model.addAttribute("user", user);
		
		List<Authority> aList = new ArrayList<Authority>();
		aList = manageAuthoritiesService.getAllAuthorities();
		Authority authority = new Authority();
		
		for(Authority auth : aList) {
			if(auth.getId() == userId) {
				authority = auth;
				break;
			}
		}
		
		model.addAttribute("authority", authority);
		
		return "edituser";
	}
	
	@RequestMapping(value = "/updatefilleduserform")
	String updateFilledUserForm(Model model, @RequestParam Map<String, String> params) {
		
		User user = new User();
		user.setUserId(Integer.parseInt(params.get("userId")));
		user.setUsername(params.get("username"));
		if(params.get("password").equals(params.get("presentPassword"))) {
			user.setPassword(params.get("password"));
		} else {
			user.setPassword(new BCryptPasswordEncoder().encode(params.get("password")));
		}
		user.setEnabled(Integer.parseInt(params.get("enabled")));

		Authority authority = new Authority();
		authority.setId(Integer.parseInt(params.get("userId")));
		authority.setUsername(params.get("username"));
		authority.setAuthority(params.get("authority"));
		
		manageUsersService.updateUser(user);
		manageAuthoritiesService.updateAuthority(authority);

		List<User> uList = new ArrayList<User>();
		uList = manageUsersService.getAllUsers();
		model.addAttribute("userList", uList);
		
		List<Authority> aList = new ArrayList<Authority>();
		aList = manageAuthoritiesService.getAllAuthorities();
		model.addAttribute("authorityList", aList);
		
		return "additional";
	}
	
}
