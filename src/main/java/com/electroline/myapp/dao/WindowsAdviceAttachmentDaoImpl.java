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

import com.electroline.myapp.domain.WindowsAdviceAttachment;

@Repository(value = "windowsAdviceAttachmentDao")
public class WindowsAdviceAttachmentDaoImpl implements WindowsAdviceAttachmentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewWindowsAttachment(WindowsAdviceAttachment waa) throws Exception {
		final String sql = "insert into windows_advices_attachments(file_name, windows_advice_id)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
				ps.setString(1, waa.getFileName());
				ps.setInt(2, waa.getWindowsAdviceId());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public WindowsAdviceAttachment getWindowsAttachment(int windowsAdviceAttachmentId) {
		final String sql = "select * from windows_advices_attachments where id = :windowsAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("windowsAdviceAttachmentId", windowsAdviceAttachmentId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<WindowsAdviceAttachment>() {

			@Override
			public WindowsAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
				waa.setId(rs.getInt("id"));
				waa.setFileName(rs.getString("file_name"));
				waa.setWindowsAdviceId(rs.getInt("windows_advice_id"));
				return waa;
			}
		});
	}

	@Override
	public List<WindowsAdviceAttachment> getAllWindowsAttachments() {
		final String sql = "select * from windows_advices_attachments";
		return jdbcTemplate.query(sql, new RowMapper<WindowsAdviceAttachment>() {

			@Override
			public WindowsAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
				waa.setId(rs.getInt("id"));
				waa.setFileName(rs.getString("file_name"));
				waa.setWindowsAdviceId(rs.getInt("windows_advice_id"));
				return waa;
			}
			
		});
	}

	@Override
	public void deleteAttachment(int windowsAdviceAttachmentId) {
		final String sql = "delete from windows_advices_attachments where id = :windowsAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("windowsAdviceAttachmentId", windowsAdviceAttachmentId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void deleteAllWindowsAttachmentsForWindowsNoteId(int windowsNoteId) {
		final String sql = "delete from windows_advices_attachments where windows_advice_id = :windowsNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("windowsNoteId", windowsNoteId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public List<WindowsAdviceAttachment> getAllWindowsAttachmentsForWindowsNoteId(int windowsNoteId) {
		final String sql = "select * from windows_advices_attachments where windows_advice_id = :windowsNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("windowsNoteId", windowsNoteId);
		return namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<WindowsAdviceAttachment>() {

			@Override
			public WindowsAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				WindowsAdviceAttachment waa = new WindowsAdviceAttachment();
				waa.setId(rs.getInt("id"));
				waa.setFileName(rs.getString("file_name"));
				waa.setWindowsAdviceId(rs.getInt("windows_advice_id"));
				return waa;
			}
			
		});
	}

}
