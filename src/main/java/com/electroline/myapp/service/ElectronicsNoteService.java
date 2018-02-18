package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.ElectronicsGeneralAdvice;

public interface ElectronicsNoteService {
	int createNewNote(ElectronicsGeneralAdvice ega) throws Exception;
	ElectronicsGeneralAdvice getNote(int electronicsGeneralAdviceId);
	List<ElectronicsGeneralAdvice> getAllNotes();
	void deleteNote(int ElectronicsGeneralAdviceId);
	void updateNote(ElectronicsGeneralAdvice ega);
}
