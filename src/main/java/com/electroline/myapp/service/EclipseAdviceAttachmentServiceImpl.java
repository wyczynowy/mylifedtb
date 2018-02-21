package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.EclipseAdviceAttachmentDao;
import com.electroline.myapp.domain.EclipseAdviceAttachment;

@Service("eclipseAdviceAttachmentService")
public class EclipseAdviceAttachmentServiceImpl implements EclipseAdviceAttachmentService {

	@Autowired
	@Qualifier("eclipseAdviceAttachmentDao")
	EclipseAdviceAttachmentDao eclipseAdviceAttachmentDao;
	
	@Override
	@Transactional
	public int createNewEclipseAttachment(EclipseAdviceAttachment eaa) throws Exception {
		return eclipseAdviceAttachmentDao.createNewEclipseAttachment(eaa);
	}

	@Override
	public EclipseAdviceAttachment getEclipseAttachment(int eclipseAdviceAttachmentId) {
		return eclipseAdviceAttachmentDao.getEclipseAttachment(eclipseAdviceAttachmentId);
	}

	@Override
	public List<EclipseAdviceAttachment> getAllEclipseAttachments() {
		return eclipseAdviceAttachmentDao.getAllEclipseAttachments();
	}

	@Override
	public List<EclipseAdviceAttachment> getAllEclipseAttachmentsForEclipseNoteId(int eclipseNoteId) {
		return eclipseAdviceAttachmentDao.getAllEclipseAttachmentsForEclipseNoteId(eclipseNoteId);
	}

	@Override
	public void deleteAttachment(int eclipseAdviceAttachmentId) {
		eclipseAdviceAttachmentDao.deleteAttachment(eclipseAdviceAttachmentId);
	}

	@Override
	public void deleteAllEclipseAttachmentsForEclipseNoteId(int eclipseNoteId) {
		eclipseAdviceAttachmentDao.deleteAllEclipseAttachmentsForEclipseNoteId(eclipseNoteId);
	}

}
