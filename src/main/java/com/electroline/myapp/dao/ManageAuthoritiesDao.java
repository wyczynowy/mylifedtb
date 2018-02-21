package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.Authority;

public interface ManageAuthoritiesDao {
	int createAuthority(Authority authority) throws Exception;
	Authority getAuthority(int id);
	List<Authority> getAllAuthorities();
	void deleteAuthority(int id);
	void updateAuthority(Authority authority);
}
