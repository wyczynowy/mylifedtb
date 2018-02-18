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

import com.electroline.myapp.domain.MicroprocessorAdvice;

@Repository(value = "microprocessorNoteDao")
public class MicroprocessorNoteDaoImpl implements MicroprocessorNoteDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public int createNewNote(final MicroprocessorAdvice ma) {
		final String sql = "insert into microprocessor_advice(advice_name, advice_description)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] { "advice_id" });
				ps.setString(1, ma.getAdviceName());
				ps.setString(2, ma.getAdviceDescription());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public MicroprocessorAdvice getNote(final int microprocessorAdviceId) {
		final String sql = "select * from microprocessor_advice where advice_id = :microprocessorAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("microprocessorAdviceId", microprocessorAdviceId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<MicroprocessorAdvice>() {

			@Override
			public MicroprocessorAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				MicroprocessorAdvice ma = new MicroprocessorAdvice();
				ma.setId(rs.getInt("advice_id"));
				ma.setAdviceName(rs.getString("advice_name"));
				ma.setAdviceDescription(rs.getString("advice_description"));
				return ma;
			}
			
		});
	}

	@Override
	public List<MicroprocessorAdvice> getAllNotes() {
		final String sql = "select * from microprocessor_advice";
		return jdbcTemplate.query(sql, new RowMapper<MicroprocessorAdvice>() {

			@Override
			public MicroprocessorAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				MicroprocessorAdvice ma = new MicroprocessorAdvice();
				ma.setId(rs.getInt("advice_id"));
				ma.setAdviceName(rs.getString("advice_name"));
				ma.setAdviceDescription(rs.getString("advice_description"));
				return ma;
			}
			
		});
	}

	@Override
	public void deleteNote(int microprocessorAdviceId) {
		final String sql = "delete from microprocessor_advice where advice_id = :microprocessorAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("microprocessorAdviceId", microprocessorAdviceId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void updateNote(MicroprocessorAdvice ma) {
		jdbcTemplate.update("update microprocessor_advice set advice_name = ? where advice_id = ?", ma.getAdviceName(), ma.getId());
		jdbcTemplate.update("update microprocessor_advice set advice_description = ? where advice_id = ?", ma.getAdviceDescription(), ma.getId());
	}

}
