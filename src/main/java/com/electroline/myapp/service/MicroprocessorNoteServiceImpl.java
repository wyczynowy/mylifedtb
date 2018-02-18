package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.MicroprocessorNoteDao;
import com.electroline.myapp.domain.MicroprocessorAdvice;

@Service(value = "microprocessorNoteService")
public class MicroprocessorNoteServiceImpl implements MicroprocessorNoteService{

	@Autowired
	@Qualifier(value = "microprocessorNoteDao")
	private MicroprocessorNoteDao myNewNoteDao;
	
	@Override
	@Transactional
	public int createNewNote(MicroprocessorAdvice ma) throws Exception {
		// -- create new note
		return myNewNoteDao.createNewNote(ma);
	}

	@Override
	public MicroprocessorAdvice getNote(int microprocessorAdviceId) {
		return myNewNoteDao.getNote(microprocessorAdviceId);
	}

	@Override
	public List<MicroprocessorAdvice> getAllNotes() {
		return myNewNoteDao.getAllNotes();
	}

	@Override
	public void deleteNote(int microprocessorAdviceId) {
		myNewNoteDao.deleteNote(microprocessorAdviceId);
		
	}

	@Override
	public void updateNote(MicroprocessorAdvice ma) {
		myNewNoteDao.updateNote(ma);
		
	}

}
