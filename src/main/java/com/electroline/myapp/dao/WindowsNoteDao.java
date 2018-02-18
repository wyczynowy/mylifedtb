package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.WindowsAdvice;

public interface WindowsNoteDao {
	int createNewNote(WindowsAdvice wa) throws Exception;
	WindowsAdvice getNote(int windowsAdviceId);
	List<WindowsAdvice> getAllNotes();
	void deleteNote(int windowsAdviceId);
	void updateNote(WindowsAdvice wa);
}
