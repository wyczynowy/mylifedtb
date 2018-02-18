package com.electroline.myapp.domain;

public class LinuxAdviceAttachment {
	private int id;
	private String fileName;
	private int linuxAdviceId;
	
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
	public int getLinuxAdviceId() {
		return linuxAdviceId;
	}
	public void setLinuxAdviceId(int linuxAdviceId) {
		this.linuxAdviceId = linuxAdviceId;
	}
}
