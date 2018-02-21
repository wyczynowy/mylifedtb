package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.MySqlAdviceAttachment;

public interface MySqlAdviceAttachmentDao {
	int createNewMySqlAttachment(MySqlAdviceAttachment maa) throws Exception;
	MySqlAdviceAttachment getMySqlAttachment(int mySqlAdviceAttachmentId);
	List<MySqlAdviceAttachment> getAllMySqlAttachments();
	List<MySqlAdviceAttachment> getAllMySqlAttachmentsForMySqlNoteId(int mySqlNoteId);
	void deleteAttachment(int mySqlAdviceAttachmentId);
	void deleteAllMySqlAttachmentsForMySqlNoteId(int mySqlNoteId);
}
