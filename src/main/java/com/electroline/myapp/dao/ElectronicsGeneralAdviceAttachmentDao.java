package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.ElectronicsGeneralAdviceAttachment;

public interface ElectronicsGeneralAdviceAttachmentDao {
	int createNewElectronicsGeneralAttachment(ElectronicsGeneralAdviceAttachment eaa) throws Exception;
	ElectronicsGeneralAdviceAttachment getElectronicsGeneralAttachment(int electronicsGeneralAdviceAttachmentId);
	List<ElectronicsGeneralAdviceAttachment> getAllElectronicsGeneralAttachments();
	List<ElectronicsGeneralAdviceAttachment> getAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(int electronicsGeneralNoteId);
	void deleteAttachment(int electronicsGeneralAdviceAttachmentId);
	void deleteAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(int electronicsGeneralNoteId);
}
