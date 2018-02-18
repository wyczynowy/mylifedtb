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

import com.electroline.myapp.domain.LinuxAdvice;

@Repository(value = "linuxNoteDao")
public class LinuxNoteDaoImpl implements LinuxNoteDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewNote(LinuxAdvice la) throws Exception {
		final String sql = "insert into linux_advice(advice_name, advice_description)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] { "advice_id" });
				ps.setString(1, la.getAdviceName());
				ps.setString(2, la.getAdviceDescription());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public LinuxAdvice getNote(int linuxAdviceId) {
		final String sql = "select * from linux_advice where advice_id = :linuxAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("linuxAdviceId", linuxAdviceId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<LinuxAdvice>() {

			@Override
			public LinuxAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				LinuxAdvice la = new LinuxAdvice();
				la.setId(rs.getInt("advice_id"));
				la.setAdviceName(rs.getString("advice_name"));
				la.setAdviceDescription(rs.getString("advice_description"));
				return la;
			}
			
		});
	}

	@Override
	public List<LinuxAdvice> getAllNotes() {
		final String sql = "select * from linux_advice";
		return jdbcTemplate.query(sql, new RowMapper<LinuxAdvice>() {

			@Override
			public LinuxAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				LinuxAdvice la = new LinuxAdvice();
				la.setId(rs.getInt("advice_id"));
				la.setAdviceName(rs.getString("advice_name"));
				la.setAdviceDescription(rs.getString("advice_description"));
				return la;
			}
			
		});
	}

	@Override
	public void deleteNote(int linuxAdviceId) {
		final String sql = "delete from linux_advice where advice_id = :linuxAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("linuxAdviceId", linuxAdviceId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void updateNote(LinuxAdvice la) {
		jdbcTemplate.update("update linux_advice set advice_name = ? where advice_id = ?", la.getAdviceName(), la.getId());
		jdbcTemplate.update("update linux_advice set advice_description = ? where advice_id = ?", la.getAdviceDescription(), la.getId());
	
	}

}
