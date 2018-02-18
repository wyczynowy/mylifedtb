package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.CAdvice;

public interface CNoteDao {
	int createNewNote(CAdvice ca) throws Exception;
	CAdvice getNote(int cAdviceId);
	List<CAdvice> getAllNotes();
	void deleteNote(int cAdviceId);
	void updateNote(CAdvice ca);
}
