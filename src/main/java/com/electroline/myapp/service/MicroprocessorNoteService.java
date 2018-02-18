package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.MicroprocessorAdvice;

public interface MicroprocessorNoteService {
	int createNewNote(MicroprocessorAdvice ma) throws Exception;
	MicroprocessorAdvice getNote(int microprocessorAdviceId);
	List<MicroprocessorAdvice> getAllNotes();
	void deleteNote(int microprocessorAdviceId);
	void updateNote(MicroprocessorAdvice ma);
}
