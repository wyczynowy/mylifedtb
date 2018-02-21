package com.electroline.myapp.domain;

public class MySqlAdviceAttachment {
	private int id;
	private String fileName;
	private int mySqlAdviceId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getMySqlAdviceId() {
		return mySqlAdviceId;
	}
	public void setMySqlAdviceId(int mySqlAdviceId) {
		this.mySqlAdviceId = mySqlAdviceId;
	}

}
