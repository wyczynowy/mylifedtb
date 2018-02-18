package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.electroline.myapp.dao.CNoteDao;
import com.electroline.myapp.domain.CAdvice;

@Service(value = "cNoteService")
public class CNoteServiceImpl implements CNoteService{
	
	@Autowired
	@Qualifier(value = "cNoteDao")
	CNoteDao myCNoteDao;

	@Override
	public int createNewNote(CAdvice ca) throws Exception {
		return myCNoteDao.createNewNote(ca);
	}

	@Override
	public CAdvice getNote(int cAdviceId) {
		return myCNoteDao.getNote(cAdviceId);
	}

	@Override
	public List<CAdvice> getAllNotes() {
		return myCNoteDao.getAllNotes();
	}

	@Override
	public void deleteNote(int cAdviceId) {
		myCNoteDao.deleteNote(cAdviceId);
	}

	@Override
	public void updateNote(CAdvice ca) {
		myCNoteDao.updateNote(ca);
	}

}
