package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.JavaAdvice;

public interface JavaNoteService {
	int createNewNote(JavaAdvice ja) throws Exception;
	JavaAdvice getNote(int javaAdviceId);
	List<JavaAdvice> getAllNotes();
	void deleteNote(int javaAdviceId);
	void updateNote(JavaAdvice ja);
}
