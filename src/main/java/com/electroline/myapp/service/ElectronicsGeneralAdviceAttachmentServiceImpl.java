package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.ElectronicsGeneralAdviceAttachmentDao;
import com.electroline.myapp.domain.ElectronicsGeneralAdviceAttachment;

@Service("electronicsGeneralAdviceAttachmentService")
public class ElectronicsGeneralAdviceAttachmentServiceImpl implements ElectronicsGeneralAdviceAttachmentService {

	@Autowired
	@Qualifier("electronicsGeneralAdviceAttachmentDao")
	ElectronicsGeneralAdviceAttachmentDao electronicsGeneralAdviceAttachmentDao;
	
	@Override
	@Transactional
	public int createNewElectronicsGeneralAttachment(ElectronicsGeneralAdviceAttachment eaa) throws Exception {
		return electronicsGeneralAdviceAttachmentDao.createNewElectronicsGeneralAttachment(eaa);
	}

	@Override
	public ElectronicsGeneralAdviceAttachment getElectronicsGeneralAttachment(
			int electronicsGeneralAdviceAttachmentId) {
		return electronicsGeneralAdviceAttachmentDao.getElectronicsGeneralAttachment(electronicsGeneralAdviceAttachmentId);
	}

	@Override
	public List<ElectronicsGeneralAdviceAttachment> getAllElectronicsGeneralAttachments() {
		return electronicsGeneralAdviceAttachmentDao.getAllElectronicsGeneralAttachments();
	}

	@Override
	public List<ElectronicsGeneralAdviceAttachment> getAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(
			int electronicsGeneralNoteId) {
		return electronicsGeneralAdviceAttachmentDao.getAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(electronicsGeneralNoteId);
	}

	@Override
	public void deleteAttachment(int electronicsGeneralAdviceAttachmentId) {
		electronicsGeneralAdviceAttachmentDao.deleteAttachment(electronicsGeneralAdviceAttachmentId);
	}

	@Override
	public void deleteAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(int electronicsGeneralNoteId) {
		electronicsGeneralAdviceAttachmentDao.deleteAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(electronicsGeneralNoteId);
	}

}
