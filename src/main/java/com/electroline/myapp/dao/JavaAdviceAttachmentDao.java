package com.electroline.myapp.dao;

import java.util.List;

import com.electroline.myapp.domain.JavaAdviceAttachment;

public interface JavaAdviceAttachmentDao {
	int createNewJavaAttachment(JavaAdviceAttachment jaa) throws Exception;
	JavaAdviceAttachment getJavaAttachment(int javaAdviceAttachmentId);
	List<JavaAdviceAttachment> getAllJavaAttachments();
	List<JavaAdviceAttachment> getAllJavaAttachmentsForJavaNoteId(int javaNoteId);
	void deleteAttachment(int javaAdviceAttachmentId);
	void deleteAllJavaAttachmentsForJavaNoteId(int javaNoteId);
}
