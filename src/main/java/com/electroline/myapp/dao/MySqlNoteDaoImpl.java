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

import com.electroline.myapp.domain.MySqlAdvice;

@Repository(value = "mySqlNoteDao")
public class MySqlNoteDaoImpl implements MySqlNoteDao {
	
	

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewNote(MySqlAdvice msa) throws Exception {
		final String sql = "insert into my_sql_advice(advice_name, advice_description)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] { "advice_id" });
				ps.setString(1, msa.getAdviceName());
				ps.setString(2, msa.getAdviceDescription());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public MySqlAdvice getNote(int mySqlAdviceId) {

		final String sql = "select * from my_sql_advice where advice_id = :mySqlAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("mySqlAdviceId", mySqlAdviceId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<MySqlAdvice>() {

			@Override
			public MySqlAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				MySqlAdvice msa = new MySqlAdvice();
				msa.setId(rs.getInt("advice_id"));
				msa.setAdviceName(rs.getString("advice_name"));
				msa.setAdviceDescription(rs.getString("advice_description"));
				return msa;
			}
			
		});
	}

	@Override
	public List<MySqlAdvice> getAllNotes() {
		final String sql = "select * from my_sql_advice";
		return jdbcTemplate.query(sql, new RowMapper<MySqlAdvice>() {

			@Override
			public MySqlAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				MySqlAdvice msa = new MySqlAdvice();
				msa.setId(rs.getInt("advice_id"));
				msa.setAdviceName(rs.getString("advice_name"));
				msa.setAdviceDescription(rs.getString("advice_description"));
				return msa;
			}
			
		});
	}

	@Override
	public void deleteNote(int mySqlAdviceId) {
		final String sql = "delete from my_sql_advice where advice_id = :mySqlAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("mySqlAdviceId", mySqlAdviceId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void updateNote(MySqlAdvice msa) {
		jdbcTemplate.update("update my_sql_advice set advice_name = ? where advice_id = ?", msa.getAdviceName(), msa.getId());
		jdbcTemplate.update("update my_sql_advice set advice_description = ? where advice_id = ?", msa.getAdviceDescription(), msa.getId());
	}

}
