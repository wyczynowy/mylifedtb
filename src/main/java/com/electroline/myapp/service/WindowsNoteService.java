package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.WindowsAdvice;

public interface WindowsNoteService {
	int createNewNote(WindowsAdvice wa) throws Exception;
	WindowsAdvice getNote(int windowsAdviceId);
	List<WindowsAdvice> getAllNotes();
	void deleteNote(int windowsAdviceId);
	void updateNote(WindowsAdvice wa);
}
