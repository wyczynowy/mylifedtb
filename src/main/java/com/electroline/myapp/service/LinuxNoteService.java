package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.LinuxAdvice;

public interface LinuxNoteService {
	int createNewNote(LinuxAdvice la) throws Exception;
	LinuxAdvice getNote(int linuxAdviceId);
	List<LinuxAdvice> getAllNotes();
	void deleteNote(int LinuxNoteAdviceId);
	void updateNote(LinuxAdvice la);
}
