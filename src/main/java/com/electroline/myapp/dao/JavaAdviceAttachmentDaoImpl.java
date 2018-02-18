package com.electroline.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.electroline.myapp.domain.JavaAdviceAttachment;

@Repository(value = "javaAdviceAttachmentDao")
public class JavaAdviceAttachmentDaoImpl implements JavaAdviceAttachmentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewJavaAttachment(JavaAdviceAttachment jaa) throws Exception {
		final String sql = "insert into java_advices_attachments(file_name, java_advice_id)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
				ps.setString(1, jaa.getFileName());
				ps.setInt(2, jaa.getJavaAdviceId());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public JavaAdviceAttachment getJavaAttachment(int javaAdviceAttachmentId) {
		final String sql = "select * from java_advices_attachments where id = :javaAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("javaAdviceAttachmentId", javaAdviceAttachmentId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<JavaAdviceAttachment>() {

			@Override
			public JavaAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				JavaAdviceAttachment jaa = new JavaAdviceAttachment();
				jaa.setId(rs.getInt("id"));
				jaa.setFileName(rs.getString("file_name"));
				jaa.setJavaAdviceId(rs.getInt("java_advice_id"));
				return jaa;
			}
			
		});
	}

	@Override
	public List<JavaAdviceAttachment> getAllJavaAttachments() {
		final String sql = "select * from java_advices_attachments";
		return jdbcTemplate.query(sql, new RowMapper<JavaAdviceAttachment>() {

			@Override
			public JavaAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				JavaAdviceAttachment jaa = new JavaAdviceAttachment();
				jaa.setId(rs.getInt("id"));
				jaa.setFileName(rs.getString("file_name"));
				jaa.setJavaAdviceId(rs.getInt("java_advice_id"));
				return jaa;
			}
			
		});
	}

	@Override
	public List<JavaAdviceAttachment> getAllJavaAttachmentsForJavaNoteId(int javaNoteId) {
		final String sql = "select * from java_advices_attachments where java_advice_id = :javaNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("javaNoteId", javaNoteId);
		return namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<JavaAdviceAttachment>() {

			@Override
			public JavaAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				JavaAdviceAttachment jaa = new JavaAdviceAttachment();
				jaa.setId(rs.getInt("id"));
				jaa.setFileName(rs.getString("file_name"));
				jaa.setJavaAdviceId(rs.getInt("java_advice_id"));
				return jaa;
			}
			
		});
	}

	@Override
	public void deleteAttachment(int javaAdviceAttachmentId) {
		final String sql = "delete from java_advices_attachments where id = :javaAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("javaAdviceAttachmentId", javaAdviceAttachmentId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void deleteAllJavaAttachmentsForJavaNoteId(int javaNoteId) {
		final String sql = "delete from java_advices_attachments where java_advice_id = :javaNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("javaNoteId", javaNoteId);
		namedParameterJdbcTemplate.update(sql, namedParameters);

	}

}
