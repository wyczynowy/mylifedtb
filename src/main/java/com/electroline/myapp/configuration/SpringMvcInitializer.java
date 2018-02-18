package com.electroline.myapp.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

import com.electroline.myapp.configuration.SpringMvcConfiguration;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5MB

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { /*SpringRootConfiguration.class, WebSecurityConfig.class*/ };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringMvcConfiguration.class, WebSecurityConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        // upload temp file will put here
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        // register a MultipartConfigElement
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

        registration.setMultipartConfig(multipartConfigElement);

    }

}