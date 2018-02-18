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

import com.electroline.myapp.domain.CAdviceAttachment;

@Repository(value = "cAdviceAttachmentDao")
public class CAdviceAttachmentDaoImpl implements CAdviceAttachmentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewCAttachment(CAdviceAttachment caa) throws Exception {
		final String sql = "insert into c_advices_attachments(file_name, c_advice_id)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
				ps.setString(1, caa.getFileName());
				ps.setInt(2, caa.getcAdviceId());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public CAdviceAttachment getCAttachment(int cAdviceAttachmentId) {
		final String sql = "select * from c_advices_attachments where id = :cAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("cAdviceAttachmentId", cAdviceAttachmentId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<CAdviceAttachment>() {

			@Override
			public CAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				CAdviceAttachment caa = new CAdviceAttachment();
				caa.setId(rs.getInt("id"));
				caa.setFileName(rs.getString("file_name"));
				caa.setcAdviceId(rs.getInt("c_advice_id"));
				return caa;
			}
			
		});
	}

	@Override
	public List<CAdviceAttachment> getAllCAttachments() {
		final String sql = "select * from c_advices_attachments";
		return jdbcTemplate.query(sql, new RowMapper<CAdviceAttachment>() {

			@Override
			public CAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				CAdviceAttachment caa = new CAdviceAttachment();
				caa.setId(rs.getInt("id"));
				caa.setFileName(rs.getString("file_name"));
				caa.setcAdviceId(rs.getInt("c_advice_id"));
				return caa;
			}
			
		});
	}

	@Override
	public List<CAdviceAttachment> getAllCAttachmentsForCNoteId(int cNoteId) {
		final String sql = "select * from c_advices_attachments where c_advice_id = :cNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("cNoteId", cNoteId);
		return namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<CAdviceAttachment>() {

			@Override
			public CAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				CAdviceAttachment caa = new CAdviceAttachment();
				caa.setId(rs.getInt("id"));
				caa.setFileName(rs.getString("file_name"));
				caa.setcAdviceId(rs.getInt("c_advice_id"));
				return caa;
			}
			
		});
	}

	@Override
	public void deleteAttachment(int cAdviceAttachmentId) {
		final String sql = "delete from c_advices_attachments where id = :cAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("cAdviceAttachmentId", cAdviceAttachmentId);
		namedParameterJdbcTemplate.update(sql, namedParameters);

	}

	@Override
	public void deleteAllCAttachmentsForCNoteId(int cNoteId) {
		final String sql = "delete from c_advices_attachments where c_advice_id = :cNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("cNoteId", cNoteId);
		namedParameterJdbcTemplate.update(sql, namedParameters);

	}

}
