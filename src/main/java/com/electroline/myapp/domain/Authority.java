package com.electroline.myapp.domain;

public class Authority implements Comparable<Authority>{

	private int id;
	private String username;
	private String authority;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}

	
	@Override
	public int compareTo(Authority o) {
		return username.compareTo(o.getUsername());
	}

}
