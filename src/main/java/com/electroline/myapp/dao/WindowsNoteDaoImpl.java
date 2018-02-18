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

import com.electroline.myapp.domain.WindowsAdvice;

@Repository(value = "windowsNoteDao")
public class WindowsNoteDaoImpl implements WindowsNoteDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public int createNewNote(WindowsAdvice wa) throws Exception {
		final String sql = "insert into windows_advice(advice_name, advice_description)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[] { "advice_id" });
				ps.setString(1, wa.getAdviceName());
				ps.setString(2, wa.getAdviceDescription());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public WindowsAdvice getNote(int windowsAdviceId) {
		final String sql = "select * from windows_advice where advice_id = :windowsAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("windowsAdviceId", windowsAdviceId);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<WindowsAdvice>() {

			@Override
			public WindowsAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				WindowsAdvice wa = new WindowsAdvice();
				wa.setId(rs.getInt("advice_id"));
				wa.setAdviceName(rs.getString("advice_name"));
				wa.setAdviceDescription(rs.getString("advice_description"));
				return wa;
			}
			
		});
	}

	@Override
	public List<WindowsAdvice> getAllNotes() {
		final String sql = "select * from windows_advice";
		return jdbcTemplate.query(sql, new RowMapper<WindowsAdvice>() {

			@Override
			public WindowsAdvice mapRow(ResultSet rs, int rowNum) throws SQLException {
				WindowsAdvice wa = new WindowsAdvice();
				wa.setId(rs.getInt("advice_id"));
				wa.setAdviceName(rs.getString("advice_name"));
				wa.setAdviceDescription(rs.getString("advice_description"));
				return wa;
			}
			
		});
	}

	@Override
	public void deleteNote(int windowsAdviceId) {
		final String sql = "delete from windows_advice where advice_id = :windowsAdviceId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("windowsAdviceId", windowsAdviceId);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void updateNote(WindowsAdvice wa) {
		jdbcTemplate.update("update windows_advice set advice_name = ? where advice_id = ?", wa.getAdviceName(), wa.getId());
		jdbcTemplate.update("update windows_advice set advice_description = ? where advice_id = ?", wa.getAdviceDescription(), wa.getId());
	}

}
