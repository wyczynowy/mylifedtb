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
import com.electroline.myapp.domain.EclipseAdvice;

@Repository(value = "eclipseNoteDao")
public class EclipseNoteDaoImpl implements EclipseNoteDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewNote(EclipseAdvice ea) throws Exception {
		final String sql = "insert eclipse_advice(advice_name, advice_description)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] { "advice_id" });
				ps.setString(1, ea.getAdviceName());
				ps.setString(2, ea.getAdviceDescription());
				return ps;
			}
			
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public EclipseAdvice getNote(int eclipseAdviceId) {
		final String sql = "select * from eclipse_advice where advice_id = :eclipseAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("eclipseAdviceId", eclipseAdviceId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<EclipseAdvice>() {

			@Override
			public EclipseAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				EclipseAdvice ea = new EclipseAdvice();
				ea.setId(rs.getInt("advice_id"));
				ea.setAdviceName(rs.getString("advice_name"));
				ea.setAdviceDescription(rs.getString("advice_description"));
				return ea;
			}
			
		});
	}

	@Override
	public List<EclipseAdvice> getAllNotes() {
		final String sql = "select * from eclipse_advice";
		return jdbcTemplate.query(sql, new RowMapper<EclipseAdvice>() {

			@Override
			public EclipseAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				EclipseAdvice ea = new EclipseAdvice();
				ea.setId(rs.getInt("advice_id"));
				ea.setAdviceName(rs.getString("advice_name"));
				ea.setAdviceDescription(rs.getString("advice_description"));
				return ea;
			}
			
		});
	}

	@Override
	public void deleteNote(int eclipseAdviceId) {
		final String sql = "delete from eclipse_advice where advice_id = :eclipseAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("eclipseAdviceId", eclipseAdviceId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void updateNote(EclipseAdvice ea) {
		jdbcTemplate.update("update eclipse_advice set advice_name = ? where advice_id = ?", ea.getAdviceName(), ea.getId());
		jdbcTemplate.update("update eclipse_advice set advice_description = ? where advice_id = ?", ea.getAdviceDescription(), ea.getId());
	}

}
