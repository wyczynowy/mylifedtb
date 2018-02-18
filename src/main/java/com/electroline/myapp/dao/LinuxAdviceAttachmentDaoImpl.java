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

import com.electroline.myapp.domain.LinuxAdviceAttachment;

@Repository(value = "linuxAdviceAttachmentDao")
public class LinuxAdviceAttachmentDaoImpl implements LinuxAdviceAttachmentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public int createNewLinuxAttachment(LinuxAdviceAttachment laa) throws Exception {
		final String sql = "insert into linux_advices_attachments(file_name, linux_advice_id)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
				ps.setString(1, laa.getFileName());
				ps.setInt(2, laa.getLinuxAdviceId());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public LinuxAdviceAttachment getLinuxAttachment(int linuxAdviceAttachmentId) {
		final String sql = "select * from linux_advices_attachments where linux_advice_id = :linuxAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("linuxAdviceAttachmentId", linuxAdviceAttachmentId);
		
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<LinuxAdviceAttachment>() {

			@Override
			public LinuxAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
				laa.setId(rs.getInt("id"));
				laa.setFileName(rs.getString("file_name"));
				laa.setLinuxAdviceId(rs.getInt("linux_advice_id"));
				return laa;
			}
			
		});
	}

	@Override
	public List<LinuxAdviceAttachment> getAllLinuxAttachments() {
		final String sql = "select * from linux_advices_attachments";
		return jdbcTemplate.query(sql, new RowMapper<LinuxAdviceAttachment>() {

			@Override
			public LinuxAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
				laa.setId(rs.getInt("id"));
				laa.setFileName(rs.getString("file_name"));
				laa.setLinuxAdviceId(rs.getInt("linux_advice_id"));
				return laa;
			}
		});
	}

	@Override
	public List<LinuxAdviceAttachment> getAllLinuxAttachmentsForLinuxNoteId(int linuxNoteId) {
		final String sql = "select * from linux_advices_attachments where linux_advice_id = :linuxNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("linuxNoteId", linuxNoteId);
		return namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<LinuxAdviceAttachment>() {

			@Override
			public LinuxAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				LinuxAdviceAttachment laa = new LinuxAdviceAttachment();
				laa.setId(rs.getInt("id"));
				laa.setFileName(rs.getString("file_name"));
				laa.setLinuxAdviceId(rs.getInt("linux_advice_id"));
				return laa;
			}
		});
	}

	@Override
	public void deleteAttachment(int linuxAdviceAttachmentId) {
		final String sql = "delete from linux_advices_attachments where id = :linuxAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("linuxAdviceAttachmentId", linuxAdviceAttachmentId);
		namedParameterJdbcTemplate.update(sql, namedParameters);		
	}

	@Override
	public void deleteAllLinuxAttachmentsForLinuxNoteId(int linuxNoteId) {
		final String sql = "delete from linux_advices_attachments where linux_advice_id = :linuxNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("linuxNoteId", linuxNoteId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

}
