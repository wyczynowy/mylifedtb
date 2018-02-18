package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.LinuxAdvice;

public interface LinuxNoteDao {
	int createNewNote(LinuxAdvice la) throws Exception;
	LinuxAdvice getNote(int linuxAdviceId);
	List<LinuxAdvice> getAllNotes();
	void deleteNote(int linuxAdviceId);
	void updateNote(LinuxAdvice la);
}
