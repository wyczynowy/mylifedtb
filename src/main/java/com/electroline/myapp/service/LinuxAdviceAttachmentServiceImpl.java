package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.LinuxAdviceAttachmentDao;
import com.electroline.myapp.domain.LinuxAdviceAttachment;

@Service(value = "linuxAdviceAttachmentService")
public class LinuxAdviceAttachmentServiceImpl implements LinuxAdviceAttachmentService {
	
	@Autowired
	@Qualifier(value = "linuxAdviceAttachmentDao")
	LinuxAdviceAttachmentDao linuxAdviceAttachmentDao;

	@Transactional
	@Override
	public int createNewLinuxAttachment(LinuxAdviceAttachment laa) throws Exception {
		return linuxAdviceAttachmentDao.createNewLinuxAttachment(laa);
	}

	@Override
	public LinuxAdviceAttachment getLinuxAttachment(int linuxAdviceAttachmentId) {
		return linuxAdviceAttachmentDao.getLinuxAttachment(linuxAdviceAttachmentId);
	}

	@Override
	public List<LinuxAdviceAttachment> getAllLinuxAttachments() {
		return linuxAdviceAttachmentDao.getAllLinuxAttachments();
	}

	@Override
	public List<LinuxAdviceAttachment> getAllLinuxAttachmentsForLinuxNoteId(int linuxNoteId) {
		return linuxAdviceAttachmentDao.getAllLinuxAttachmentsForLinuxNoteId(linuxNoteId);
	}

	@Override
	public void deleteAttachment(int linuxAdviceAttachmentId) {
		linuxAdviceAttachmentDao.deleteAttachment(linuxAdviceAttachmentId);

	}

	@Override
	public void deleteAllLinuxAttachmentsForLinuxNoteId(int linuxNoteId) {
		linuxAdviceAttachmentDao.deleteAllLinuxAttachmentsForLinuxNoteId(linuxNoteId);

	}

}
