package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.MySqlAdvice;

public interface MySqlNoteService {
	int createNewNote(MySqlAdvice msa) throws Exception;
	MySqlAdvice getNote(int mySqlAdviceId);
	List<MySqlAdvice> getAllNotes();
	void deleteNote(int mySqlAdviceId);
	void updateNote(MySqlAdvice msa);
}
