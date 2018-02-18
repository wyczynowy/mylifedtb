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

import com.electroline.myapp.domain.JavaAdvice;

@Repository(value = "javaNoteDao")
public class JavaNoteDaoImpl implements JavaNoteDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewNote(JavaAdvice ja) throws Exception {
		final String sql = "insert into java_advice(advice_name, advice_description)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] { "advice_id" });
				ps.setString(1, ja.getAdviceName());
				ps.setString(2, ja.getAdviceDescription());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public JavaAdvice getNote(int javaAdviceId) {
		final String sql = "select * from java_advice where advice_id = :javaAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("javaAdviceId", javaAdviceId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<JavaAdvice>() {

			@Override
			public JavaAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				JavaAdvice ja = new JavaAdvice();
				ja.setId(rs.getInt("advice_id"));
				ja.setAdviceName(rs.getString("advice_name"));
				ja.setAdviceDescription(rs.getString("advice_description"));
				return ja;
			}
			
		});
	}

	@Override
	public List<JavaAdvice> getAllNotes() {
		final String sql = "select * from java_advice";
		return jdbcTemplate.query(sql, new RowMapper<JavaAdvice>() {

			@Override
			public JavaAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				JavaAdvice ja = new JavaAdvice();
				ja.setId(rs.getInt("advice_id"));
				ja.setAdviceName(rs.getString("advice_name"));
				ja.setAdviceDescription(rs.getString("advice_description"));
				return ja;
			}
			
		});
	}

	@Override
	public void deleteNote(int javaAdviceId) {
		final String sql = "delete from java_advice where advice_id = :javaAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("javaAdviceId", javaAdviceId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void updateNote(JavaAdvice ja) {
		jdbcTemplate.update("update java_advice set advice_name = ? where advice_id = ?", ja.getAdviceName(), ja.getId());
		jdbcTemplate.update("update java_advice set advice_description = ? where advice_id = ?", ja.getAdviceDescription(), ja.getId());	
	}

}
