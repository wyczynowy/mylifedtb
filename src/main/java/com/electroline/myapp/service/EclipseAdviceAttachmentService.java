package com.electroline.myapp.service;

import java.util.List;

import com.electroline.myapp.domain.EclipseAdviceAttachment;

public interface EclipseAdviceAttachmentService {
	int createNewEclipseAttachment(EclipseAdviceAttachment eaa) throws Exception;
	EclipseAdviceAttachment getEclipseAttachment(int eclipseAdviceAttachmentId);
	List<EclipseAdviceAttachment> getAllEclipseAttachments();
	List<EclipseAdviceAttachment> getAllEclipseAttachmentsForEclipseNoteId(int eclipseNoteId);
	void deleteAttachment(int eclipseAdviceAttachmentId);
	void deleteAllEclipseAttachmentsForEclipseNoteId(int eclipseNoteId);
}
