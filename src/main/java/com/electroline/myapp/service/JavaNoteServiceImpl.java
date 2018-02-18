package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.electroline.myapp.dao.JavaNoteDao;
import com.electroline.myapp.domain.JavaAdvice;

@Service(value = "javaNoteService")
public class JavaNoteServiceImpl implements JavaNoteService{
	
	@Autowired
	@Qualifier(value = "javaNoteDao")
	JavaNoteDao myJavaNoteDao;

	@Override
	public int createNewNote(JavaAdvice ja) throws Exception {
		return myJavaNoteDao.createNewNote(ja);
	}

	@Override
	public JavaAdvice getNote(int javaAdviceId) {
		return myJavaNoteDao.getNote(javaAdviceId);
	}

	@Override
	public List<JavaAdvice> getAllNotes() {
		return myJavaNoteDao.getAllNotes();
	}

	@Override
	public void deleteNote(int javaAdviceId) {
		myJavaNoteDao.deleteNote(javaAdviceId);
		
	}

	@Override
	public void updateNote(JavaAdvice ja) {
		myJavaNoteDao.updateNote(ja);
	}

}
