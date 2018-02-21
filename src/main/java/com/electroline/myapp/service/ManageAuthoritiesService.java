package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.Authority;

public interface ManageAuthoritiesService {
	int createAuthority(Authority authority) throws Exception;
	Authority getAuthority(int id);
	List<Authority> getAllAuthorities();
	void deleteAuthority(int id);
	void updateAuthority(Authority authority);
}
