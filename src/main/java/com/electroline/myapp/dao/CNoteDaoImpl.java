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

import com.electroline.myapp.domain.CAdvice;

@Repository(value = "cNoteDao")
public class CNoteDaoImpl implements CNoteDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewNote(CAdvice ca) throws Exception {
		final String sql = "insert c_advice(advice_name, advice_description)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] { "advice_id" });
				ps.setString(1, ca.getAdviceName());
				ps.setString(2, ca.getAdviceDescription());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public CAdvice getNote(int cAdviceId) {
		final String sql = "select * from c_advice where advice_id = :cAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("cAdviceId", cAdviceId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<CAdvice>() {

			@Override
			public CAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				CAdvice ca = new CAdvice();
				ca.setId(rs.getInt("advice_id"));
				ca.setAdviceName(rs.getString("advice_name"));
				ca.setAdviceDescription(rs.getString("advice_description"));
				return ca;
			}
			
		});
	}

	@Override
	public List<CAdvice> getAllNotes() {
		final String sql = "select * from c_advice";
		return jdbcTemplate.query(sql, new RowMapper<CAdvice>() {

			@Override
			public CAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				CAdvice ca = new CAdvice();
				ca.setId(rs.getInt("advice_id"));
				ca.setAdviceName(rs.getString("advice_name"));
				ca.setAdviceDescription(rs.getString("advice_description"));
				return ca;
			}
			
		});
	}

	@Override
	public void deleteNote(int cAdviceId) {
		final String sql = "delete from c_advice where advice_id = :cAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("cAdviceId", cAdviceId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	
	}

	@Override
	public void updateNote(CAdvice ca) {
		jdbcTemplate.update("update c_advice set advice_name = ? where advice_id = ?", ca.getAdviceName(), ca.getId());
		jdbcTemplate.update("update c_advice set advice_description = ? where advice_id = ?", ca.getAdviceDescription(), ca.getId());
	
	}

}
