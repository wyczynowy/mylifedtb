package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.MicroprocessorAdviceAttachmentDao;
import com.electroline.myapp.domain.MicroprocessorAdviceAttachment;

@Service("microprocessorAdviceAttachmentService")
public class MicroprocessorAdviceAttachmentServiceImpl implements MicroprocessorAdviceAttachmentService {

	@Autowired
	@Qualifier("microprocessorAdviceAttachmentDao")
	MicroprocessorAdviceAttachmentDao microprocessorAdviceAttachmentDao;
	
	@Override
	@Transactional
	public int createNewMicroprocessorAttachment(MicroprocessorAdviceAttachment maa) throws Exception {
		return microprocessorAdviceAttachmentDao.createNewMicroprocessorAttachment(maa);
	}

	@Override
	public MicroprocessorAdviceAttachment getMicroprocessorAttachment(int microprocessorAdviceAttachmentId) {
		return microprocessorAdviceAttachmentDao.getMicroprocessorAttachment(microprocessorAdviceAttachmentId);
	}

	@Override
	public List<MicroprocessorAdviceAttachment> getAllMicroprocessorAttachments() {
		return microprocessorAdviceAttachmentDao.getAllMicroprocessorAttachments();
	}

	@Override
	public List<MicroprocessorAdviceAttachment> getAllMicroprocessorAttachmentsForMicroprocessorNoteId(
			int microprocessorNoteId) {
		return microprocessorAdviceAttachmentDao.getAllMicroprocessorAttachmentsForMicroprocessorNoteId(microprocessorNoteId);
	}

	@Override
	public void deleteAttachment(int microprocessorAdviceAttachmentId) {
		microprocessorAdviceAttachmentDao.deleteAttachment(microprocessorAdviceAttachmentId);
	}

	@Override
	public void deleteAllMicroprocessorAttachmentsForMicroprocessorNoteId(int microprocessorNoteId) {
		microprocessorAdviceAttachmentDao.deleteAllMicroprocessorAttachmentsForMicroprocessorNoteId(microprocessorNoteId);
	}

}
