package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.MySqlNoteDao;
import com.electroline.myapp.domain.MySqlAdvice;

@Service(value = "mySqlNoteService")
public class MySqlNoteServiceImpl implements MySqlNoteService {

	@Autowired
	@Qualifier(value = "mySqlNoteDao")
	private MySqlNoteDao mySqlNoteDao;
	
	@Override
	@Transactional
	public int createNewNote(MySqlAdvice msa) throws Exception {
		return mySqlNoteDao.createNewNote(msa);
	}

	@Override
	public MySqlAdvice getNote(int mySqlAdviceId) {
		return mySqlNoteDao.getNote(mySqlAdviceId);
	}

	@Override
	public List<MySqlAdvice> getAllNotes() {
		return mySqlNoteDao.getAllNotes();
	}

	@Override
	public void deleteNote(int mySqlAdviceId) {
		mySqlNoteDao.deleteNote(mySqlAdviceId);
		
	}

	@Override
	public void updateNote(MySqlAdvice msa) {
		mySqlNoteDao.updateNote(msa);
		
	}

}
