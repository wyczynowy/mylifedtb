package com.electroline.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.dao.JavaAdviceAttachmentDao;
import com.electroline.myapp.domain.JavaAdviceAttachment;

@Service(value = "javaAdviceAttachmentService")
public class JavaAdviceAttachmentServiceImpl implements JavaAdviceAttachmentService {

	@Autowired
	@Qualifier(value = "javaAdviceAttachmentDao")
	JavaAdviceAttachmentDao javaAdviceAttachmentDao;
	
	@Transactional
	@Override
	public int createNewJavaAttachment(JavaAdviceAttachment jaa) throws Exception {
		return javaAdviceAttachmentDao.createNewJavaAttachment(jaa);
	}

	@Override
	public JavaAdviceAttachment getJavaAttachment(int javaAdviceAttachmentId) {
		return javaAdviceAttachmentDao.getJavaAttachment(javaAdviceAttachmentId);
	}

	@Override
	public List<JavaAdviceAttachment> getAllJavaAttachments() {
		return javaAdviceAttachmentDao.getAllJavaAttachments();
	}

	@Override
	public List<JavaAdviceAttachment> getAllJavaAttachmentsForJavaNoteId(int javaNoteId) {
		return javaAdviceAttachmentDao.getAllJavaAttachmentsForJavaNoteId(javaNoteId);
	}

	@Override
	public void deleteAttachment(int javaAdviceAttachmentId) {
		javaAdviceAttachmentDao.deleteAttachment(javaAdviceAttachmentId);

	}

	@Override
	public void deleteAllJavaAttachmentsForJavaNoteId(int javaNoteId) {
		javaAdviceAttachmentDao.deleteAllJavaAttachmentsForJavaNoteId(javaNoteId);

	}

}
