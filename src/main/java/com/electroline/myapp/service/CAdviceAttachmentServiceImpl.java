package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.CAdviceAttachmentDao;
import com.electroline.myapp.domain.CAdviceAttachment;

@Service(value = "cAdviceAttachmentService")
public class CAdviceAttachmentServiceImpl implements CAdviceAttachmentService {

	@Autowired
	@Qualifier(value = "cAdviceAttachmentDao")
	private CAdviceAttachmentDao cAdviceAttachmentDao;
	
	@Transactional
	@Override
	public int createNewCAttachment(CAdviceAttachment caa) throws Exception {
		return cAdviceAttachmentDao.createNewCAttachment(caa);
	}

	@Override
	public CAdviceAttachment getCAttachment(int cAdviceAttachmentId) {
		return cAdviceAttachmentDao.getCAttachment(cAdviceAttachmentId);
	}

	@Override
	public List<CAdviceAttachment> getAllCAttachments() {
		return cAdviceAttachmentDao.getAllCAttachments();
	}

	@Override
	public List<CAdviceAttachment> getAllCAttachmentsForCNoteId(int cNoteId) {
		return cAdviceAttachmentDao.getAllCAttachmentsForCNoteId(cNoteId);
	}

	@Override
	public void deleteAttachment(int cAdviceAttachmentId) {
		cAdviceAttachmentDao.deleteAttachment(cAdviceAttachmentId);

	}

	@Override
	public void deleteAllCAttachmentsForCNoteId(int cNoteId) {
		cAdviceAttachmentDao.deleteAllCAttachmentsForCNoteId(cNoteId);

	}

}
