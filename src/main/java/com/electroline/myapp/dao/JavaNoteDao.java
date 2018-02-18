package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.JavaAdvice;

public interface JavaNoteDao {
	int createNewNote(JavaAdvice ja) throws Exception;
	JavaAdvice getNote(int javaAdviceId);
	List<JavaAdvice> getAllNotes();
	void deleteNote(int javaAdviceId);
	void updateNote(JavaAdvice ja);
}
