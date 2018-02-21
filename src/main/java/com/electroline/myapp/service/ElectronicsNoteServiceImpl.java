package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.ElectronicsNoteDao;
import com.electroline.myapp.domain.ElectronicsGeneralAdvice;

@Service(value = "electronicsNoteService")
public class ElectronicsNoteServiceImpl implements ElectronicsNoteService {
	
	@Autowired
	@Qualifier(value = "electronicsNoteDao")
	private ElectronicsNoteDao myElectronicsNoteDao;

	@Override
	@Transactional
	public int createNewNote(ElectronicsGeneralAdvice ega) throws Exception {
		return myElectronicsNoteDao.createNewNote(ega);
	}

	@Override
	public ElectronicsGeneralAdvice getNote(int electronicsGeneralAdviceId) {
		return myElectronicsNoteDao.getNote(electronicsGeneralAdviceId);
	}

	@Override
	public List<ElectronicsGeneralAdvice> getAllNotes() {
		return myElectronicsNoteDao.getAllNotes();
	}

	@Override
	public void deleteNote(int ElectronicsGeneralAdviceId) {
		myElectronicsNoteDao.deleteNote(ElectronicsGeneralAdviceId);
		
	}

	@Override
	public void updateNote(ElectronicsGeneralAdvice ega) {
		myElectronicsNoteDao.updateNote(ega);
		
	}

}
