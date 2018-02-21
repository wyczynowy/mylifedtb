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

import com.electroline.myapp.domain.MySqlAdviceAttachment;

@Repository("mySqlAdviceAttachmentDao")
public class MySqlAdviceAttachmentDaoImpl implements MySqlAdviceAttachmentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewMySqlAttachment(MySqlAdviceAttachment maa) throws Exception {
		final String sql = "insert into my_sql_advices_attachments(file_name, my_sql_advice_id)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
				ps.setString(1, maa.getFileName());
				ps.setInt(2, maa.getMySqlAdviceId());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public MySqlAdviceAttachment getMySqlAttachment(int mySqlAdviceAttachmentId) {
		final String sql = "select * from my_sql_advices_attachments where id = :mySqlAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("mySqlAdviceAttachmentId", mySqlAdviceAttachmentId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<MySqlAdviceAttachment>() {

			@Override
			public MySqlAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
				maa.setId(rs.getInt("id"));
				maa.setFileName(rs.getString("file_name"));
				maa.setMySqlAdviceId(rs.getInt("my_sql_advice_id"));
				return maa;
			}
		});
	}

	@Override
	public List<MySqlAdviceAttachment> getAllMySqlAttachments() {
		final String sql = "select * from my_sql_advices_attachments";
		return jdbcTemplate.query(sql, new RowMapper<MySqlAdviceAttachment>() {

			@Override
			public MySqlAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
				maa.setId(rs.getInt("id"));
				maa.setFileName(rs.getString("file_name"));
				maa.setMySqlAdviceId(rs.getInt("my_sql_advice_id"));
				return maa;
			}
			
		});
	}

	@Override
	public List<MySqlAdviceAttachment> getAllMySqlAttachmentsForMySqlNoteId(int mySqlNoteId) {
		final String sql = "select * from my_sql_advices_attachments where my_sql_advice_id = :mySqlNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("mySqlNoteId", mySqlNoteId);
		return namedParameterJdbcTemplate.query(sql, namedParameters, new RowMapper<MySqlAdviceAttachment>() {

			@Override
			public MySqlAdviceAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				MySqlAdviceAttachment maa = new MySqlAdviceAttachment();
				maa.setId(rs.getInt("id"));
				maa.setFileName(rs.getString("file_name"));
				maa.setMySqlAdviceId(rs.getInt("my_sql_advice_id"));
				return maa;
			}
			
		});
	}

	@Override
	public void deleteAttachment(int mySqlAdviceAttachmentId) {
		final String sql = "delete from my_sql_advices_attachments where id = :mySqlAdviceAttachmentId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("mySqlAdviceAttachmentId", mySqlAdviceAttachmentId);
		namedParameterJdbcTemplate.update(sql, namedParameters);

	}

	@Override
	public void deleteAllMySqlAttachmentsForMySqlNoteId(int mySqlNoteId) {
		final String sql = "delete from my_sql_advices_attachments where my_sql_advice_id = :mySqlNoteId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("mySqlNoteId", mySqlNoteId);
		namedParameterJdbcTemplate.update(sql, namedParameters);

	}

}
