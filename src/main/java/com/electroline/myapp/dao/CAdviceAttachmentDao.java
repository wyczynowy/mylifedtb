package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.CAdviceAttachment;

public interface CAdviceAttachmentDao {
	int createNewCAttachment(CAdviceAttachment caa) throws Exception;
	CAdviceAttachment getCAttachment(int cAdviceAttachmentId);
	List<CAdviceAttachment> getAllCAttachments();
	List<CAdviceAttachment> getAllCAttachmentsForCNoteId(int cNoteId);
	void deleteAttachment(int cAdviceAttachmentId);
	void deleteAllCAttachmentsForCNoteId(int cNoteId);
}
