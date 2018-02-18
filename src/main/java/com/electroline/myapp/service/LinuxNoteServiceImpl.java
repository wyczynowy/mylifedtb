package com.electroline.myapp.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.electroline.myapp.dao.LinuxNoteDao;
import com.electroline.myapp.domain.LinuxAdvice;

@Service(value = "linuxNoteService")
public class LinuxNoteServiceImpl implements LinuxNoteService {
	
	@Autowired
	@Qualifier(value = "linuxNoteDao")
	LinuxNoteDao myLinuxNoteDao;

	@Override
	public int createNewNote(LinuxAdvice la) throws Exception {
		return myLinuxNoteDao.createNewNote(la);
	}

	@Override
	public LinuxAdvice getNote(int linuxAdviceId) {
		return myLinuxNoteDao.getNote(linuxAdviceId);
	}

	@Override
	public List<LinuxAdvice> getAllNotes() {
		return myLinuxNoteDao.getAllNotes();
	}

	@Override
	public void deleteNote(int LinuxNoteAdviceId) {
		myLinuxNoteDao.deleteNote(LinuxNoteAdviceId);
		
	}

	@Override
	public void updateNote(LinuxAdvice la) {
		myLinuxNoteDao.updateNote(la);
		
	}

}
