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
import org.springframework.transaction.annotation.Transactional;

import com.electroline.myapp.domain.Authority;

@Repository(value = "manageAuthoritiesDao")
public class ManageAuthoritiesDaoImpl implements ManageAuthoritiesDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	@Transactional
	public int createAuthority(Authority authority) throws Exception {
		final String sql = "insert into authorities(username, authority)"
				+ "values(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String [] {"id"});
				ps.setString(1, authority.getUsername());
				ps.setString(2, authority.getAuthority());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public Authority getAuthority(int id) {
		final String sql = "select * from authorities where id = :id";
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
		return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<Authority>() {

			@Override
			public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
				Authority authority = new Authority();
				authority.setId(rs.getInt("id"));
				authority.setUsername(rs.getString("username"));
				authority.setAuthority(rs.getString("authority"));
				return authority;
			}
		});
	}

	@Override
	public List<Authority> getAllAuthorities() {
		final String sql = "select * from authorities";
		return jdbcTemplate.query(sql, new RowMapper<Authority>() {

			@Override
			public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
				Authority authority = new Authority();
				authority.setId(rs.getInt("id"));
				authority.setUsername(rs.getString("username"));
				authority.setAuthority(rs.getString("authority"));
				return authority;
			}
		});
	}

	@Override
	public void deleteAuthority(int id) {
		final String sql = "delete from authorities where id = :id";
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
		namedParameterJdbcTemplate.update(sql, namedParameters);
	}

	@Override
	public void updateAuthority(Authority authority) {
		jdbcTemplate.update("update authorities set username = ? where id = ?", authority.getUsername(), authority.getId());
		jdbcTemplate.update("update authorities set authority = ? where id = ?", authority.getAuthority(), authority.getId());
	}

}
