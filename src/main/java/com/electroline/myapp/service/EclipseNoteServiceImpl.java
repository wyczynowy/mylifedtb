package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electroline.myapp.dao.EclipseNoteDao;
import com.electroline.myapp.domain.EclipseAdvice;

@Service(value = "eclipseNoteService")
public class EclipseNoteServiceImpl implements EclipseNoteService {

	@Autowired
	private EclipseNoteDao myEclipseNoteDao;
	
	@Override
	public int createNewNote(EclipseAdvice ea) throws Exception {
		return myEclipseNoteDao.createNewNote(ea);
	}

	@Override
	public EclipseAdvice getNote(int eclipseAdviceId) {
		return myEclipseNoteDao.getNote(eclipseAdviceId);
	}

	@Override
	public List<EclipseAdvice> getAllNotes() {
		return myEclipseNoteDao.getAllNotes();
	}

	@Override
	public void deleteNote(int eclipseAdviceId) {
		myEclipseNoteDao.deleteNote(eclipseAdviceId);
		
	}

	@Override
	public void updateNote(EclipseAdvice ea) {
		myEclipseNoteDao.updateNote(ea);
		
	}

}
