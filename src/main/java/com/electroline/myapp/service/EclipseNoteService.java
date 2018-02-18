package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.EclipseAdvice;

public interface EclipseNoteService {
	int createNewNote(EclipseAdvice ea) throws Exception;
	EclipseAdvice getNote(int eclipseAdviceId);
	List<EclipseAdvice> getAllNotes();
	void deleteNote(int eclipseAdviceId);
	void updateNote(EclipseAdvice ea);
}
