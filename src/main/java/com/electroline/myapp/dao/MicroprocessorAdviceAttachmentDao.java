package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.MicroprocessorAdviceAttachment;


public interface MicroprocessorAdviceAttachmentDao {
	int createNewMicroprocessorAttachment(MicroprocessorAdviceAttachment maa) throws Exception;
	MicroprocessorAdviceAttachment getMicroprocessorAttachment(int microprocessorAdviceAttachmentId);
	List<MicroprocessorAdviceAttachment> getAllMicroprocessorAttachments();
	List<MicroprocessorAdviceAttachment> getAllMicroprocessorAttachmentsForMicroprocessorNoteId(int microprocessorNoteId);
	void deleteAttachment(int microprocessorAdviceAttachmentId);
	void deleteAllMicroprocessorAttachmentsForMicroprocessorNoteId(int microprocessorNoteId);
}
