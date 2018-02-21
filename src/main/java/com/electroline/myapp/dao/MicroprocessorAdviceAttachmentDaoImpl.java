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

import com.electroline.myapp.domain.MicroprocessorAdviceAttachment;

@Repository("microprocessorAdviceAttachmentDao")
public class MicroprocessorAdviceAttachmentDaoImpl implements MicroprocessorAdviceAttachmentDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewMicroprocessorAttachment(MicroprocessorAdviceAttachment maa) throws Exception {
		final String sql = "insert into microprocessor_advices_attachments(file_name, microprocessor_advice_id)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
				ps.setString(1, maa.getFileName());
				ps.setInt(2, maa.getMicroprocessorAdviceId());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public MicroprocessorAdviceAttachment getMicroprocessorAttachment(int microprocessorAdviceAttachmentId) {
		final String sql = "select * from microprocessor_advices_attachments where id = :microprocessorAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("microprocessorAdviceAttachmentId", microprocessorAdviceAttachmentId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<MicroprocessorAdviceAttachment>() {

			@Override
			public MicroprocessorAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
				maa.setId(rs.getInt("id"));
				maa.setFileName(rs.getString("file_name"));
				maa.setMicroprocessorAdviceId(rs.getInt("microprocessor_advice_id"));
				return maa;
			}	
		});
	}

	@Override
	public List<MicroprocessorAdviceAttachment> getAllMicroprocessorAttachments() {
		final String sql = "select * from microprocessor_advices_attachments";
		return jdbcTemplate.query(sql, new RowMapper<MicroprocessorAdviceAttachment>() {

			@Override
			public MicroprocessorAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
				maa.setId(rs.getInt("id"));
				maa.setFileName(rs.getString("file_name"));
				maa.setMicroprocessorAdviceId(rs.getInt("microprocessor_advice_id"));
				return maa;
			}
		});
	}

	@Override
	public List<MicroprocessorAdviceAttachment> getAllMicroprocessorAttachmentsForMicroprocessorNoteId(
			int microprocessorNoteId) {
		final String sql = "select * from microprocessor_advices_attachments where microprocessor_advice_id = :microprocessorNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("microprocessorNoteId", microprocessorNoteId);
		return namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<MicroprocessorAdviceAttachment>() {

			@Override
			public MicroprocessorAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				MicroprocessorAdviceAttachment maa = new MicroprocessorAdviceAttachment();
				maa.setId(rs.getInt("id"));
				maa.setFileName(rs.getString("file_name"));
				maa.setMicroprocessorAdviceId(rs.getInt("microprocessor_advice_id"));
				return maa;
			}
			
		});
	}

	@Override
	public void deleteAttachment(int microprocessorAdviceAttachmentId) {
		final String sql = "delete from microprocessor_advices_attachments where id = :microprocessorAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("microprocessorAdviceAttachmentId", microprocessorAdviceAttachmentId);
		namedParameterJdbcTemplate.update(sql, namedParameters);

	}

	@Override
	public void deleteAllMicroprocessorAttachmentsForMicroprocessorNoteId(int microprocessorNoteId) {
		final String sql = "delete from microprocessor_advices_attachments where microprocessor_advice_id = :microprocessorNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("microprocessorNoteId", microprocessorNoteId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

}
