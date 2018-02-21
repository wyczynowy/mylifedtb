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

import com.electroline.myapp.domain.ElectronicsGeneralAdviceAttachment;

@Repository("electronicsGeneralAdviceAttachmentDao")
public class ElectronicsGeneralAdviceAttachmentDaoImpl implements ElectronicsGeneralAdviceAttachmentDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewElectronicsGeneralAttachment(ElectronicsGeneralAdviceAttachment eaa) throws Exception {
		final String sql = "insert into electronics_general_advices_attachments(file_name, electronics_general_advice_id)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
				ps.setString(1, eaa.getFileName());
				ps.setInt(2, eaa.getElectronicsGeneralAdviceId());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public ElectronicsGeneralAdviceAttachment getElectronicsGeneralAttachment(
			int electronicsGeneralAdviceAttachmentId) {
		final String sql = "select * from electronics_general_advices_attachments where id = :electronicsGeneralAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("electronicsGeneralAdviceAttachmentId", electronicsGeneralAdviceAttachmentId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<ElectronicsGeneralAdviceAttachment>() {

			@Override
			public ElectronicsGeneralAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
				eaa.setId(rs.getInt("id"));
				eaa.setFileName(rs.getString("file_name"));
				eaa.setElectronicsGeneralAdviceId(rs.getInt("electronics_general_advice_id"));
				return eaa;
			}
			
		});
	}

	@Override
	public List<ElectronicsGeneralAdviceAttachment> getAllElectronicsGeneralAttachments() {
		final String sql = "select * from electronics_general_advices_attachments";
		return jdbcTemplate.query(sql, new RowMapper<ElectronicsGeneralAdviceAttachment>() {

			@Override
			public ElectronicsGeneralAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
				eaa.setId(rs.getInt("id"));
				eaa.setFileName(rs.getString("file_name"));
				eaa.setElectronicsGeneralAdviceId(rs.getInt("electronics_general_advice_id"));
				return eaa;
			}
			
		});
	}

	@Override
	public List<ElectronicsGeneralAdviceAttachment> getAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(
			int electronicsGeneralNoteId) {
		final String sql = "select * from electronics_general_advices_attachments where electronics_general_advice_id = :electronicsGeneralNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("electronicsGeneralNoteId", electronicsGeneralNoteId);
		return namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<ElectronicsGeneralAdviceAttachment>() {

			@Override
			public ElectronicsGeneralAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				ElectronicsGeneralAdviceAttachment eaa = new ElectronicsGeneralAdviceAttachment();
				eaa.setId(rs.getInt("id"));
				eaa.setFileName(rs.getString("file_name"));
				eaa.setElectronicsGeneralAdviceId(rs.getInt("electronics_general_advice_id"));
				return eaa;
			}
			
		});
	}

	@Override
	public void deleteAttachment(int electronicsGeneralAdviceAttachmentId) {
		final String sql = "delete from electronics_general_advices_attachments where id = :electronicsGeneralAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("electronicsGeneralAdviceAttachmentId", electronicsGeneralAdviceAttachmentId);
		namedParameterJdbcTemplate.update(sql, namedParameters);

	}

	@Override
	public void deleteAllElectronicsGeneralAttachmentsForElectronicsGeneralNoteId(int electronicsGeneralNoteId) {
		final String sql = "delete from electronics_general_advices_attachments where electronics_general_advice_id = :electronicsGeneralNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("electronicsGeneralNoteId", electronicsGeneralNoteId);
		namedParameterJdbcTemplate.update(sql, namedParameters);

	}

}
