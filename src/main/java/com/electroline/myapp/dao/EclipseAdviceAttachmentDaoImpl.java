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

import com.electroline.myapp.domain.EclipseAdviceAttachment;

@Repository("eclipseAdviceAttachmentDao")
public class EclipseAdviceAttachmentDaoImpl implements EclipseAdviceAttachmentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public int createNewEclipseAttachment(EclipseAdviceAttachment eaa) throws Exception {
		final String sql = "insert into eclipse_advices_attachments(file_name, eclipse_advice_id)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
				ps.setString(1, eaa.getFileName());
				ps.setInt(2, eaa.getEclipseAdviceId());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public EclipseAdviceAttachment getEclipseAttachment(int eclipseAdviceAttachmentId) {
		final String sql = "select * from eclipse_advices_attachments where id = :eclipseAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("eclipseAdviceAttachmentId", eclipseAdviceAttachmentId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<EclipseAdviceAttachment>() {

			@Override
			public EclipseAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
				eaa.setId(rs.getInt("id"));
				eaa.setFileName(rs.getString("file_name"));
				eaa.setEclipseAdviceId(rs.getInt("eclipse_advice_id"));
				return eaa;
			}
			
		});
	}

	@Override
	public List<EclipseAdviceAttachment> getAllEclipseAttachments() {
		final String sql = "select * from eclipse_advices_attachments";
		return jdbcTemplate.query(sql, new RowMapper<EclipseAdviceAttachment>() {

			@Override
			public EclipseAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
				eaa.setId(rs.getInt("id"));
				eaa.setFileName(rs.getString("file_name"));
				eaa.setEclipseAdviceId(rs.getInt("eclipse_advice_id"));
				return eaa;
			}
			
		});
	}

	@Override
	public List<EclipseAdviceAttachment> getAllEclipseAttachmentsForEclipseNoteId(int eclipseNoteId) {
		final String sql = "select * from eclipse_advices_attachments where eclipse_advice_id = :eclipseNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("eclipseNoteId", eclipseNoteId);
		return namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<EclipseAdviceAttachment>() {

			@Override
			public EclipseAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				EclipseAdviceAttachment eaa = new EclipseAdviceAttachment();
				eaa.setId(rs.getInt("id"));
				eaa.setFileName(rs.getString("file_name"));
				eaa.setEclipseAdviceId(rs.getInt("eclipse_advice_id"));
				return eaa;
			}
		});
	}

	@Override
	public void deleteAttachment(int eclipseAdviceAttachmentId) {
		final String sql = "delete from eclipse_advices_attachments where id = :eclipseAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("eclipseAdviceAttachmentId", eclipseAdviceAttachmentId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void deleteAllEclipseAttachmentsForEclipseNoteId(int eclipseNoteId) {
		final String sql = "delete from eclipse_advices_attachments where eclipse_advice_id = :eclipseNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("eclipseNoteId", eclipseNoteId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

}
