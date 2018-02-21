package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.MySqlAdviceAttachmentDao;
import com.electroline.myapp.domain.MySqlAdviceAttachment;

@Service("mySqlAdviceAttachmentService")
public class MySqlAdviceAttachmentServiceImpl implements MySqlAdviceAttachmentService {

	@Autowired
	@Qualifier("mySqlAdviceAttachmentDao")
	MySqlAdviceAttachmentDao mySqlAdviceAttachmentDao;
	
	@Override
	@Transactional
	public int createNewMySqlAttachment(MySqlAdviceAttachment maa) throws Exception {
		return mySqlAdviceAttachmentDao.createNewMySqlAttachment(maa);
	}

	@Override
	public MySqlAdviceAttachment getMySqlAttachment(int mySqlAdviceAttachmentId) {
		return mySqlAdviceAttachmentDao.getMySqlAttachment(mySqlAdviceAttachmentId);
	}

	@Override
	public List<MySqlAdviceAttachment> getAllMySqlAttachments() {
		return mySqlAdviceAttachmentDao.getAllMySqlAttachments();
	}

	@Override
	public List<MySqlAdviceAttachment> getAllMySqlAttachmentsForMySqlNoteId(int mySqlNoteId) {
		return mySqlAdviceAttachmentDao.getAllMySqlAttachmentsForMySqlNoteId(mySqlNoteId);
	}

	@Override
	public void deleteAttachment(int mySqlAdviceAttachmentId) {
		mySqlAdviceAttachmentDao.deleteAttachment(mySqlAdviceAttachmentId);
	}

	@Override
	public void deleteAllMySqlAttachmentsForMySqlNoteId(int mySqlNoteId) {
		mySqlAdviceAttachmentDao.deleteAllMySqlAttachmentsForMySqlNoteId(mySqlNoteId);
	}

}
