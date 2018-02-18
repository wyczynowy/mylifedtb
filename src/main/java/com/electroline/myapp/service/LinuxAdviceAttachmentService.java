package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.LinuxAdviceAttachment;

public interface LinuxAdviceAttachmentService {
	int createNewLinuxAttachment(LinuxAdviceAttachment laa) throws Exception;
	LinuxAdviceAttachment getLinuxAttachment(int linuxAdviceAttachmentId);
	List<LinuxAdviceAttachment> getAllLinuxAttachments();
	List<LinuxAdviceAttachment> getAllLinuxAttachmentsForLinuxNoteId(int linuxNoteId);
	void deleteAttachment(int linuxAdviceAttachmentId);
	void deleteAllLinuxAttachmentsForLinuxNoteId(int linuxNoteId);
}
