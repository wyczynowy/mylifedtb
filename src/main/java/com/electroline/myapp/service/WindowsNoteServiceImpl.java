package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.WindowsNoteDao;
import com.electroline.myapp.domain.WindowsAdvice;

@Service(value = "windowsNoteService")
public class WindowsNoteServiceImpl implements WindowsNoteService {

	@Autowired
	@Qualifier(value = "windowsNoteDao")
	WindowsNoteDao myWindowsNoteDao;
	
	@Override
	@Transactional
	public int createNewNote(WindowsAdvice wa) throws Exception {
		return myWindowsNoteDao.createNewNote(wa);
	}

	@Override
	public WindowsAdvice getNote(int windowsAdviceId) {
		return myWindowsNoteDao.getNote(windowsAdviceId);
	}

	@Override
	public List<WindowsAdvice> getAllNotes() {
		return myWindowsNoteDao.getAllNotes();
	}

	@Override
	public void deleteNote(int windowsAdviceId) {
		myWindowsNoteDao.deleteNote(windowsAdviceId);
	}

	@Override
	public void updateNote(WindowsAdvice wa) {
		myWindowsNoteDao.updateNote(wa);
	}

}
