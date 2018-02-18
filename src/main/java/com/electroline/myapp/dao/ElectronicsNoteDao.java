package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.ElectronicsGeneralAdvice;

public interface ElectronicsNoteDao {
	int createNewNote(ElectronicsGeneralAdvice ega) throws Exception;
	ElectronicsGeneralAdvice getNote(int electronicsGeneralAdviceId);
	List<ElectronicsGeneralAdvice> getAllNotes();
	void deleteNote(int ElectronicsGeneralAdviceId);
	void updateNote(ElectronicsGeneralAdvice ega);
}
