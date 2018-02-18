package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.WindowsAdviceAttachment;

public interface WindowsAdviceAttachmentService {
	int createNewWindowsAttachment(WindowsAdviceAttachment waa) throws Exception;
	WindowsAdviceAttachment getWindowsAttachment(int windowsAdviceAttachmentId);
	List<WindowsAdviceAttachment> getAllWindowsAttachments();
	List<WindowsAdviceAttachment> getAllWindowsAttachmentsForWindowsNoteId(int windowsNoteId);
	void deleteAttachment(int windowsAdviceAttachmentId);
	void deleteAllWindowsAttachmentsForWindowsNoteId(int windowsNoteId);
}
