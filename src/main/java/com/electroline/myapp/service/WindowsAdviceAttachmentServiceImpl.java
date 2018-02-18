package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.WindowsAdviceAttachmentDao;
import com.electroline.myapp.domain.WindowsAdviceAttachment;

@Service(value = "windowsAdviceAttachmentService")
public class WindowsAdviceAttachmentServiceImpl implements WindowsAdviceAttachmentService {

	@Autowired
	@Qualifier(value = "windowsAdviceAttachmentDao")
	WindowsAdviceAttachmentDao myWindowsAdviceAttachmentDao;
	
	@Override
	@Transactional
	public int createNewWindowsAttachment(WindowsAdviceAttachment waa) throws Exception {
		return myWindowsAdviceAttachmentDao.createNewWindowsAttachment(waa);
	}

	@Override
	public WindowsAdviceAttachment getWindowsAttachment(int windowsAdviceAttachmentId) {
		return myWindowsAdviceAttachmentDao.getWindowsAttachment(windowsAdviceAttachmentId);
	}

	@Override
	public List<WindowsAdviceAttachment> getAllWindowsAttachments() {
		return myWindowsAdviceAttachmentDao.getAllWindowsAttachments();
	}

	@Override
	public void deleteAttachment(int windowsAdviceAttachmentId) {
		myWindowsAdviceAttachmentDao.deleteAttachment(windowsAdviceAttachmentId);
	}

	@Override
	public void deleteAllWindowsAttachmentsForWindowsNoteId(int windowsNoteId) {
		myWindowsAdviceAttachmentDao.deleteAllWindowsAttachmentsForWindowsNoteId(windowsNoteId);
		
	}

	@Override
	public List<WindowsAdviceAttachment> getAllWindowsAttachmentsForWindowsNoteId(int windowsNoteId) {
		return myWindowsAdviceAttachmentDao.getAllWindowsAttachmentsForWindowsNoteId(windowsNoteId);
	}

}
