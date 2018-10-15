package com.electroline.myapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.electroline.myapp.util.FileLogger;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;


@Configuration
@ComponentScan(basePackages = {"com.electroline.myapp"})
@PropertySource("classpath:META-INF/spring/database.properties")
@PropertySource("classpath:META-INF/spring/fileLogger.properties")
@EnableWebMvc
public class SpringMvcConfiguration implements WebMvcConfigurer{
	
    @Value("${database.driverClassName}")
    protected String driverClassName;
    @Value("${database.url}")
    protected String url;
    @Value("${database.username}")
    protected String username;
    @Value("${database.password}")
    protected String password;
    
    @Value("${fileLogger.logFolderUrl}")
    private String logFolderUrl;
    
	@Bean(name = "fileLogger")
	public FileLogger fileLogger() {
		return new FileLogger(logFolderUrl);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	// Bean name must be "multipartResolver", by default Spring uses method name as bean name.
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

	@Bean(name="jdbcTemplate")
	public JdbcTemplate createJdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(createDataSource());
		return jdbcTemplate;
	}
	
	@Bean(name="dataSource")
	public BasicDataSource createDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
//		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setDriverClassName(driverClassName);
//		basicDataSource.setUrl("jdbc:mysql://localhost:3306/my_life_db");
		basicDataSource.setUrl(url);
//		basicDataSource.setUsername("root");
		basicDataSource.setUsername(username);
//		basicDataSource.setPassword("oklahoma");
		basicDataSource.setPassword(password);
		return basicDataSource;
	}
	
	@Bean(name="namedJdbcTemplate")
	public NamedParameterJdbcTemplate createNammedJdbcTemplate() {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(createDataSource());
		return namedParameterJdbcTemplate;
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp().prefix("/WEB-INF/views/").suffix(".jsp");
	}
	
	@Bean(name="txManager")
	public DataSourceTransactionManager createDatasourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(createDataSource());
		return dataSourceTransactionManager;
	}

}
