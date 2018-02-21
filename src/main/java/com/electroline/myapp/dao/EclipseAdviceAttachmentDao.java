package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.EclipseAdviceAttachment;

public interface EclipseAdviceAttachmentDao {
	int createNewEclipseAttachment(EclipseAdviceAttachment eaa) throws Exception;
	EclipseAdviceAttachment getEclipseAttachment(int eclipseAdviceAttachmentId);
	List<EclipseAdviceAttachment> getAllEclipseAttachments();
	List<EclipseAdviceAttachment> getAllEclipseAttachmentsForEclipseNoteId(int eclipseNoteId);
	void deleteAttachment(int eclipseAdviceAttachmentId);
	void deleteAllEclipseAttachmentsForEclipseNoteId(int eclipseNoteId);
}
