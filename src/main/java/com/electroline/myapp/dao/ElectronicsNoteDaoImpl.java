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

import com.electroline.myapp.domain.ElectronicsGeneralAdvice;

@Repository(value = "electronicsNoteDao")
public class ElectronicsNoteDaoImpl implements ElectronicsNoteDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewNote(ElectronicsGeneralAdvice ega) throws Exception {
		final String sql = "insert into electronics_general_advice(advice_name, advice_description)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] { "advice_id" });
				ps.setString(1, ega.getAdviceName());
				ps.setString(2, ega.getAdviceDescription());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public ElectronicsGeneralAdvice getNote(int electronicsGeneralAdviceId) {
		final String sql = "select * from electronics_general_advice where advice_id = :electronicsGeneralAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("electronicsGeneralAdviceId", electronicsGeneralAdviceId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<ElectronicsGeneralAdvice>() {

			@Override
			public ElectronicsGeneralAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				ElectronicsGeneralAdvice ega = new ElectronicsGeneralAdvice();
				ega.setId(rs.getInt("advice_id"));
				ega.setAdviceName(rs.getString("advice_name"));
				ega.setAdviceDescription(rs.getString("advice_description"));
				return ega;
			}
			
		});
	}

	@Override
	public List<ElectronicsGeneralAdvice> getAllNotes() {
		final String sql = "select * from electronics_general_advice";
		return jdbcTemplate.query(sql, new RowMapper<ElectronicsGeneralAdvice>() {

			@Override
			public ElectronicsGeneralAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				ElectronicsGeneralAdvice ega = new ElectronicsGeneralAdvice();
				ega.setId(rs.getInt("advice_id"));
				ega.setAdviceName(rs.getString("advice_name"));
				ega.setAdviceDescription(rs.getString("advice_description"));
				return ega;
			}
			
		});
	}

	@Override
	public void deleteNote(int ElectronicsGeneralAdviceId) {
		final String sql = "delete from electronics_general_advice where advice_id = :ElectronicsGeneralAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("ElectronicsGeneralAdviceId", ElectronicsGeneralAdviceId);
		namedParameterJdbcTemplate.update(sql, namedParameters);	
	}

	@Override
	public void updateNote(ElectronicsGeneralAdvice ega) {
		jdbcTemplate.update("update electronics_general_advice set advice_name = ? where advice_id = ?", ega.getAdviceName(), ega.getId());
		jdbcTemplate.update("update electronics_general_advice set advice_description = ? where advice_id = ?", ega.getAdviceDescription(), ega.getId());	
	}

}
