package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.MicroprocessorAdvice;

public interface MicroprocessorNoteDao {
	int createNewNote(MicroprocessorAdvice ma);
	MicroprocessorAdvice getNote(int microprocessorAdviceId);
	List<MicroprocessorAdvice> getAllNotes();
	void deleteNote(int microprocessorAdviceId);
	void updateNote(MicroprocessorAdvice ma);
}
